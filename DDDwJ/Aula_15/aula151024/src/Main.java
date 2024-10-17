import models.Canal;
import models.Mensagem;
import models.Servidor;
import models.Usuario;

import java.util.Date;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Usuario userBob = new Usuario();
        userBob.setNome("Bob");
        userBob.setUserId(UUID.randomUUID());

        Usuario userJack = new Usuario();
        userJack.setNome("Jack");
        userJack.setUserId(UUID.randomUUID());

        Servidor server1 = new Servidor();
        server1.setNome("Server do Tibia");

        Canal canal1 = new Canal();
        canal1.setNome("Canal da Hunt");
        canal1.setServidor(server1);

        Canal canal2 = new Canal();
        canal2.setNome("Canal do Farm");
        canal2.setServidor(server1);

        userBob.adicionarServidor(server1);
        userJack.adicionarServidor(server1);

        server1.adicionarCanal(canal1);
        server1.adicionarCanal(canal2);

        server1.adicionarUsuario(userBob);
        server1.adicionarUsuario(userJack);

        server1.visualizarUsuarios();

        Mensagem mensagem1 = new Mensagem();
        mensagem1.setConteudo("Bora oberon?");
        mensagem1.setUsuario(userBob);
        mensagem1.setData(new Date());

        Mensagem mensagem2 = new Mensagem();
        mensagem2.setConteudo("Só depois da hunt em issavi");
        mensagem2.setUsuario(userJack);
        mensagem2.setData(new Date());

        Mensagem mensagem3 = new Mensagem();
        mensagem3.setConteudo("vai demorar muito?");
        mensagem3.setUsuario(userBob);
        mensagem3.setData(new Date());

        canal1.adicionarMensagem(mensagem1);
        canal1.adicionarMensagem(mensagem2);
        canal1.adicionarMensagem(mensagem3);

        canal1.visualizarMensagens();

        canal1.filtrarMensagensPorUsuario(userBob);

        canal1.filtrarMensagensPorPalavraChave("issavi");

        server1.contarMensagensPorUsuario(userBob);

        server1.agruparMensagensPorUsuario();

        server1.contarMensagensNoServidor();
    }
}
