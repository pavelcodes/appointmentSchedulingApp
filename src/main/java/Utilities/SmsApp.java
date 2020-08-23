package Utilities;

import com.twilio.base.ResourceSet;
import com.twilio.rest.api.v2010.account.usage.record.Today;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;
import com.twilio.type.PhoneNumber;

import java.time.Instant;

import static Utilities.CustomerQuery.*;
import static spark.Spark.*;

public class SmsApp {

    public static String dateOfReceivedReply;

    public static void sms() {


        get("/", (req, res) -> "Hello Web");

        post("/receive-sms", (req, res) -> {
            res.type("application/xml");

            Body confirmationBody = new Body
                    .Builder("Thank you! Appointment Confirmed")
                    .build();
            Body rescheduledBody = new Body
                    .Builder("Thank you! One of our team members will call you to reschedule!")
                    .build();

            Body unKnownReplyBody = new Body
                    .Builder("Please reply with yes or no")
                    .build();

            Message confirmationSMS = new Message
                    .Builder()
                    .body(confirmationBody)
                    .build();

            Message rescheduledSMS = new Message
                    .Builder()
                    .body(rescheduledBody)
                    .build();


            Message unKnownReplySMS = new Message
                    .Builder()
                    .body(unKnownReplyBody)
                    .build();


            MessagingResponse confirmationReply = new MessagingResponse
                    .Builder()
                    .message(confirmationSMS)
                    .build();

            MessagingResponse rescheduledReply = new MessagingResponse
                    .Builder()
                    .message(rescheduledSMS)
                    .build();

            MessagingResponse unKnownReply = new MessagingResponse
                    .Builder()
                    .message(unKnownReplySMS)
                    .build();

            ResourceSet<com.twilio.rest.api.v2010.account.Message> messages = com.twilio.rest.api.v2010.account.Message.reader().read();
            dateOfReceivedReply = Instant.now().toString();
            System.out.println(dateOfReceivedReply); //gets timestamp from when message was triggered.

            for (com.twilio.rest.api.v2010.account.Message record : messages) {
                String messageId = record.getSid();
                int appointmentId;
                String body = record.getBody().toLowerCase();
                PhoneNumber phone = record.getFrom();
                String phoneString = phone.toString();
                appointmentId = matchMessageIdAndCust(phoneString);
                getReplyCust(body, dateOfReceivedReply, appointmentId);
                    if (body.equals("yes")) {
                        return confirmationReply.toXml();
                    } else if (body.equals("no")) {
                        return rescheduledReply.toXml();
                    } else {
                        return unKnownReply.toXml();
                    }
                }
            return unKnownReply.toXml();
        });

    }
}
