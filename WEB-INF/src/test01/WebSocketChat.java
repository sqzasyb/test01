package test01;

import java.util.Set;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint("/chat")
public class WebSocketChat {

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        String id = session.getId();
        echo("(" + id + ")が入室しました。", session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        String id = session.getId();
        echo("(" + id + ") " + message, session);


    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        String id = session.getId();
        echo("(" + id + ")が退出しました。", session);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    /**
     * メッセージエコー
     *
     * @param message メッセージ
     * @param session セッション
     */
    private void echo(String message, Session session) {
        System.out.println(message);
        // 開いているセッション取得
        Set<Session> sessions = session.getOpenSessions();
        // セッション数分ループ
        for (Session tmp : sessions) {
            try {
                // メッセージ送信
                System.out.println("テスト：" + message);
                tmp.getBasicRemote().sendText(message);
            } catch (Exception e) {
                // 例外の場合
                e.printStackTrace();
            }
        }
    }
}