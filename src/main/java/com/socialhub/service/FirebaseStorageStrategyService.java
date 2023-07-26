package com.socialhub.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FirebaseStorageStrategyService{
    private StorageOptions storageOptions;
    private String bucketName;
    private String projectId;



    /**
     * Método anotado con @PostConstruct que inicializa la configuración de Firebase Storage.
     * Se ejecuta después de que la instancia de la clase se haya construido y todas las
     * inyecciones de dependencia hayan sido completadas.
     *
     * @throws Exception si ocurre algún error durante la inicialización de Firebase Storage.
     */
    @PostConstruct
    private void initializeFirebase() throws Exception {
        bucketName = "socialhub-30934.appspot.com";
        projectId = "socialhub-30934";
        
        // Se crea un FileInputStream para leer el archivo de credenciales de servicio (serviceAccount.json)
        FileInputStream serviceAccount = new FileInputStream("./serviceAccount.json");
        
        // Se construye la configuración de StorageOptions utilizando el ID del proyecto y las credenciales del servicio
        this.storageOptions = StorageOptions.newBuilder()
                .setProjectId(projectId)
                .setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
    }

    

    /**
     * Método que sube un archivo a Firebase Storage.
     *
     * @param multipartFile el archivo a subir.
     * @return un arreglo de String que contiene la URL del archivo subido y el nombre del objeto en Firebase Storage.
     * @throws IOException si ocurre algún error durante la lectura o escritura del archivo.
     */
    public String[] uploadFile(MultipartFile multipartFile, Integer redSocial) throws IOException {
        File file = convertMultiPartToFile(multipartFile);
        Path filePath = file.toPath();
        
        String carpetaRedSocial="";
        if(redSocial==0) {
        	carpetaRedSocial="bookface/";
        }
        if(redSocial==1) {
        	carpetaRedSocial="toktik/";
        }

        if(redSocial==2) {
            carpetaRedSocial="comments/";
        }
        
        String objectName = carpetaRedSocial + generateFileName(multipartFile);

        Storage storage = storageOptions.getService();

        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        Blob blob = storage.create(blobInfo, Files.readAllBytes(filePath));

        // Obtener la URL del archivo subido
        String url = "https://firebasestorage.googleapis.com/v0" + blob.getSelfLink().substring(37) + "?alt=media&";

        log.info("File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);
        return new String[]{url, objectName};
    }

    /**
     * Convierte un objeto MultipartFile en un archivo de tipo File.
     *
     * @param file el archivo MultipartFile a convertir.
     * @return el archivo convertido de tipo File.
     * @throws IOException si ocurre algún error durante la escritura del archivo.
     */
    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }


    /**
     * Genera un nombre de archivo único basado en la marca de tiempo y el nombre original del archivo.
     *
     * @param multiPart el archivo MultipartFile para el cual se generará el nombre.
     * @return el nombre de archivo generado.
     */
    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + Objects.requireNonNull(multiPart.getOriginalFilename()).replace(" ", "_");
    }

    
}
