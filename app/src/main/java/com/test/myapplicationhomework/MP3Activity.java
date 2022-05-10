package com.test.myapplicationhomework;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.Timer;
import java.util.TimerTask;

public class MP3Activity extends Activity {

    private EditText editText = null;
    private Button start, stop = null;
    private ProgressBar progressBar = null;
    private ProgressBar progressBar1 = null;
    private TextView times = null;
    private TextView result = null;
    boolean state = true;
    private Handler handler;
    private static MediaPlayer mediaPlayer = null;
    private int iTimes = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mp3);

        editText = findViewById(R.id.url);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        progressBar = findViewById(R.id.schedule);
        progressBar1 = findViewById(R.id.schedule1);
        times = findViewById(R.id.times);
        result = findViewById(R.id.result);

        String url = editText.getText().toString();
        //mp3歌曲链接太长，虚拟机app界面编辑框没办法复制粘贴，在这里设置初始值，方便代码演示
        editText.setText("https://m10.music.126.net/20220509084046/97ade53c2a2a1740aa806efa1bcf92cf/yyaac/obj/wonDkMOGw6XDiTHCmMOi/4122020155/94a4/2731/cc2a/9333693f3a09f32805d5414baac8aa4a.m4a");
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.length() != 0) {
                    if (state) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                download();
                            }
                        }).start();
                        state = false;
                    } else {
                        Toast.makeText(MP3Activity.this, "正在下载，请稍后...", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MP3Activity.this, "请添加下载地址", Toast.LENGTH_SHORT).show();
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
            }
        });

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 0x101) {
                    progressBar.setProgress(msg.arg1);
                    result.setText("已下载" + String.valueOf(msg.arg1) + "%");
                    if (msg.arg1 == 100) {
                        result.setText("下载完成！");
                    }
                }
                if (msg.what == 0x102) {
                    progressBar.setProgress(msg.arg1);
                }
                super.handleMessage(msg);
            }
        };
    }


    private void download() {
        try {
            URL url = new URL(editText.getText().toString());
            URLConnection urlConnection = url.openConnection();//获取URL对应的连接
            int length = urlConnection.getContentLength(); //获得传输数据的字节
            ReadableByteChannel channel = Channels.newChannel(url.openStream()); //该通道是可以读取字节的通道
            //在任何给定时间，在可读通道ReadableByteChannel上只能进行一次读取操作。如果一个线程在通道上启动读取操作，那么尝试启动另一个读取操作的任何其他线程都将阻塞，直到第一个操作完成。
            File file = new File("data/data/com.test.myapplicationhomework/test.mp3");
            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            FileChannel channel1 = fileOutputStream.getChannel(); //FileChannel它能直接连接输入输出流的文件通道，将数据直接写入到目标文件中去。而且效率更高

            /*写入数据到Buffer
            调用flip()方法
            从Buffer中读取数据
            调用clear()方法或者compact()方法*/
            //数据写入buffer中
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int len;
            int sum = 0;
            while ((len = channel.read(byteBuffer)) != -1) {
                sum += len;
                int progress = (sum * 100) / length;
                //调用flip（）方法
                byteBuffer.flip();
                while (byteBuffer.hasRemaining()) {
                    channel1.write(byteBuffer);
                }
                byteBuffer.clear();
                Message m = handler.obtainMessage();//获得空消息对象
                m.what = 0x101;
                m.arg1 = progress;
                handler.sendMessage(m);
            }
            display();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void display() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(this, Uri.parse("data/data/com.test.myapplicationhomework/test.mp3"));
        mediaPlayer.start();
        progressBar1.setMax(mediaPlayer.getDuration());

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                progressBar1.setProgress(mediaPlayer.getCurrentPosition()); // 当前播放点
            }
        };
        timer.schedule(timerTask, 0, 10);  //firstTime为Date类型,period为long，表示从firstTime时刻开始，每隔period毫秒执行一次。

        iTimes++;
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                times.setText("" + iTimes);
                display();
            }
        });
    }
}
