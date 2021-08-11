package com.endrollmodel.notificationsample;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.databinding.DataBindingUtil;

import com.endrollmodel.notificationsample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    /** 很油的sample
     * 說明
     * 運作原理 : 每次發送的notifyID為前一次的ID++
     * 利用Group將同Group內的訊息都折疊在一起 本範例使用兩個Group
     * 7以下版本會只顯示設定的Group名稱 (即為 createTitleBody 方法內的訊息 若需要再細分則需再另外調整)
     * 7以上版本將會折疊Group的訊息 (系統內建折疊機制)
     */
    private ActivityMainBinding binding;
    // channel名稱 會顯示在通知設定內 由於這裡是動態的 安裝後並不會立刻出現通知的channel選項 需要實際運作過一次才會出現
    private final String channelOneId = "HoloLive";
    private final String channelTwoId = "Nijisanji";
    // notify的訊息分類折疊用的名稱
    private final String groupOne = "HoloMen";
    private final String groupTwo = "NijiMen";
    // 不用動
    private int notifyId = 0; // 發送的訊息id 如果相同則會覆蓋前一筆相同id的內容 若是不要僅顯示單筆資訊的話 可以做++或是random
    private int groupId = 0; // 分組的group id根據此id 會做分組

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        holoMember();
        nijiMember();
    }

    private void holoMember() {
        binding.button3.setOnClickListener(view ->
                sendNotify(new DataBean(channelOneId, groupOne, channelOneId, "您有一則" + ((TextView) view).getText().toString() + "通知")));
        binding.button4.setOnClickListener(view ->
                sendNotify(new DataBean(channelOneId, groupOne, channelOneId, "您有一則" + ((TextView) view).getText().toString() + "通知")));
        binding.button5.setOnClickListener(view ->
                sendNotify(new DataBean(channelOneId, groupOne, channelOneId, "您有一則" + ((TextView) view).getText().toString() + "通知")));
        binding.button6.setOnClickListener(view ->
                sendNotify(new DataBean(channelOneId, groupOne, channelOneId, "您有一則" + ((TextView) view).getText().toString() + "通知")));
    }

    private void nijiMember() {
        binding.button11.setOnClickListener(view ->
                sendNotify(new DataBean(channelTwoId, groupTwo, channelTwoId, "您有一則" + ((TextView) view).getText().toString() + "通知")));
        binding.button12.setOnClickListener(view ->
                sendNotify(new DataBean(channelTwoId, groupTwo, channelTwoId, "您有一則" + ((TextView) view).getText().toString() + "通知")));
        binding.button13.setOnClickListener(view ->
                sendNotify(new DataBean(channelTwoId, groupTwo, channelTwoId, "您有一則" + ((TextView) view).getText().toString() + "通知")));
        binding.button14.setOnClickListener(view ->
                sendNotify(new DataBean(channelTwoId, groupTwo, channelTwoId, "您有一則" + ((TextView) view).getText().toString() + "通知")));
    }

    /**
     * 傳送訊息的方法
     * @param data 資料來源
     */
    private void sendNotify(DataBean data) {
        // 建立Manager
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // 如果Sdk大於O 則建立頻道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = createOneChannel(data.getChannelId());
            channel.setShowBadge(true);
            notificationManager.createNotificationChannel(channel);
        }
        // 建立內容
        Notification titleBody = createTitleBody(data).build(); // 整合用訊息（標頭）
        Notification body = createNotifyBody(data).build(); // 內文
        if (data.getChannelId().equals(channelOneId)) {
            groupId = -1; // 根據需求設定notify的id 如有需求請自行更改
        }
        if (data.getChannelId().equals(channelTwoId)) {
            groupId = -2; // 根據需求設定notify的id 如有需求請自行更改
        }
        notificationManager.notify(notifyId, body);
        notificationManager.notify(groupId, titleBody);
        notifyId++; // 每次發送訊息時增加一次ID避免訊息重複覆蓋
    }

    /**
     * 建立標題訊息(整合用訊息)
     * @param data
     * @return NotificationCompat.Builder
     */
    private NotificationCompat.Builder createTitleBody(DataBean data) {
        return new NotificationCompat.Builder(this, data.getChannelId())
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(data.getTitle() + "訊息")
                .setContentText("通知")
                .setGroup(data.getGroupId())
                .setGroupSummary(true) // 是否為整合訊息的第一項
                .setStyle(new NotificationCompat.InboxStyle()
                        .addLine(data.getContent())
                        .setSummaryText(data.getTitle())) // 整合訊息會顯示的文字 可用於解釋GroupName
                .setAutoCancel(true);
    }

    /**
     * 建立Notify內容
     */
    private NotificationCompat.Builder createNotifyBody(DataBean data) {
        return new NotificationCompat.Builder(this, data.getChannelId())
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(data.getTitle())
                .setContentText(data.getContent())
                .setGroup(data.getGroupId());
    }

    /**
     * 建立的Channel盡量固定內容 除非需要動態調整 不然則綁定某一Channel ID
     * 這裡的Channel則為動態產生
     * @param channelId
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private NotificationChannel createOneChannel(String channelId) {
        // 通知設定內的顯示內容 ： channelID 顯示名稱 優先層級
        return new NotificationChannel(channelId, channelId, NotificationManager.IMPORTANCE_HIGH);
    }


}