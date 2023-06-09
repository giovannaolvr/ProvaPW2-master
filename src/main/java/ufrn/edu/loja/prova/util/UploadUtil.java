package ufrn.edu.loja.prova.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.springframework.web.multipart.MultipartFile;

public class UploadUtil {
    public static boolean fazerUploadImagem(MultipartFile imagem,String nomeArquivo){
        boolean sucessoUpload = false;
        if(!imagem.isEmpty()&&imagem.getContentType().equals("image/png")){
            try{
                //criando diretorio para armazenar arquivo
                String pastaUploadImagens = "C:\\Users\\giaol\\Desktop\\ProvaPW_-30-05-2023\\ProvaPW2-master\\src\\main\\resources\\static\\images\\img-upload";
                File dir = new File(pastaUploadImagens);
                if(!dir.exists()){
                    dir.mkdirs();
                }

                //criando arquivo no diretorio
                File serverFile = new File(dir.getAbsolutePath() + File.separator + nomeArquivo);

                BufferedOutputStream stream = new BufferedOutputStream (new FileOutputStream(serverFile)) ;
                stream.write(imagem.getBytes());
                stream.close();
                System.out.println("armazenado em: "+ serverFile.getAbsolutePath());
                System.out.println("Nome do Arquivo: "+ nomeArquivo);
                sucessoUpload = true;
            }catch(Exception e){
                System.out.println("Voce falhou em carregar o arquivo");
            }
        }
        else{
            System.out.println("Arquivo vazio");
            
        }
        return sucessoUpload;
    }
}
