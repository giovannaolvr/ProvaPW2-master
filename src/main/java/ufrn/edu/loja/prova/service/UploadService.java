package ufrn.edu.loja.prova.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;


import org.springframework.web.multipart.MultipartFile;

public class UploadService {

    public static boolean fazerUploadImagem(MultipartFile imagem){
        boolean sucessoUpload = false;
        if(!imagem.isEmpty()){
            String nomeArquivo = imagem.getOriginalFilename();
            try {
                
                //criando diretorio para armazernar o arquivo
                String pastaUploadImagem = "C:\\Users\\gomej\\OneDrive\\Área de Trabalho\\ProvaPW2\\ProvaPW2-master\\src\\main\\imagens";
                File dir = new File(pastaUploadImagem);
                if(!dir.exists()){
                    dir.mkdirs();
                }

                //criando o arquivo no direitorio
                File serverFile = new File(dir.getAbsolutePath()+File.separator+nomeArquivo);

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));

                stream.write(imagem.getBytes());
                stream.close();
                System.out.println("Armazenado em: " + serverFile.getAbsolutePath());
                System.out.println("Você fez upload do arquivo");
            }catch (Exception e) {
               System.out.print("falhou a imagem do arquivo");
            }
        }else{
            System.out.println("Arquivo vazio, mande a imagem do arquivo desejado!");
        }

        return sucessoUpload;
        
    }
}
