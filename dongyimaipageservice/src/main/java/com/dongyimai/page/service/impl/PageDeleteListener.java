package com.dongyimai.page.service.impl;

import com.dongyimai.page.service.ItemPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@Component
public class PageDeleteListener implements MessageListener {

    @Autowired
    private ItemPageService itemPageService;

    @Override
    public void onMessage(Message message) {
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            Long [] ids = (Long[])objectMessage.getObject();
            //执行删除
            itemPageService.deleteItemHtml(ids);

            System.out.println("页面删除成功...");

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
