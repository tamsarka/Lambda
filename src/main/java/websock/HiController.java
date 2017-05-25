package websock;

import java.io.IOException;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/webs")
public class HiController {
	private Session session;
	
	@OnOpen
	public void  onOpen(){
		System.out.println(" Its open now..");
	}
	
	@OnMessage
	public void onInteractio(Session session) throws IOException{
		System.out.println("<<<<<<<<<<< is it succeded ???????>>>>>>>>>>>>>>>");
		if(this.session != null && this.session.isOpen())
		{
			this.session.getBasicRemote().sendText("Let me request you also");
		}
	}

}
