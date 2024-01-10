package raf.dsw.classycraft.app.messageGenerator;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter

public class Message
{
    //Sadrzaj poruke
    private String content, type;
    private Timestamp timeStamp = new Timestamp(System.currentTimeMillis());

    public Message(String content, String type)
    {
        this.content = content;
        this.type = type;
    }
}
