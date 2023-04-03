import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {

        // fazer uma conexão HTTP e buscar os top 250 filmes
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();
        System.out.println(body);

        // pegar só os dados que interessam (titulo, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // exibir e manipular os dados
        // for (int i = 0; i <= 10; i++) {
        // Map<String, String> filme = listaDeFilmes.get(i);
        // System.out.println("\u001b[1m\u001b[31m\u001b[42mTitulo:\u001b[m " +
        // filme.get("title"));
        // System.out.println("\u001b[1m\u001b[32m\u001b[43mURL da Imagem:\u001b[m " +
        // filme.get("image"));
        // double classificacao = Double.parseDouble(filme.get("imDbRating"));
        // int numeroEstrelinhas = (int) classificacao;

        // if (numeroEstrelinhas <= 5) {
        // for (int n = 0; n <= numeroEstrelinhas; n++) {
        // System.out.print("⭐");
        // }
        // System.out.println("\n");
        // } else {
        // System.out.println("🍅");

        // }

        var diretorio = new File("figurinhas/");
        diretorio.mkdir();

        // exibir e manipular os dados 2
        var geradora = new GeradoraDeFigurinhas();
        for (Map<String, String> filme : listaDeFilmes) {

            String urlImagem = filme.get("image");
            String titulo = filme.get("title");
            double classificacao = Double.parseDouble(filme.get("imDbRating"));

            String textoFigurinha;
            InputStream imagemPessoa;
            if (classificacao < 9.0) {
                textoFigurinha = "TOPZERA";
                imagemPessoa = new FileInputStream(new File("sobreposicao/Feliz.png"));
            } else {
                textoFigurinha = "HMMMMMMMMMM....";
                imagemPessoa = new FileInputStream(new File("sobreposicao/Triste.jpg"));

            }

            InputStream inputStream = new URL(urlImagem).openStream();

            String nomeArquivo = "figurinhas/" + titulo + ".png";

            geradora.cria(inputStream, nomeArquivo, textoFigurinha, imagemPessoa);

            System.out.println(titulo);
            System.out.println();

        }

    }

}
