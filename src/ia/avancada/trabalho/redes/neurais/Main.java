/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ia.avancada.trabalho.redes.neurais;

import ia.avancada.trabalho.redes.neurais.model.TratamentoCopos;
import ia.avancada.trabalho.redes.neurais.model.TreinamentoCopos;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.imgrec.FractionRgbData;
import org.neuroph.imgrec.ImageRecognitionPlugin;
import org.opencv.core.Core;

/**
 *
 * @author Arthur
 */
public class Main {

    public static void main(String[] args) throws IOException {
        String opencvpath = System.getProperty("user.dir") + "\\lib\\";
        String libPath = System.getProperty("java.library.path");
        System.load(opencvpath + Core.NATIVE_LIBRARY_NAME + ".dll");

        String pathEntrada = "copos/nproc";
        String pathSaida = "copos/proc";

        String treino = "copos/treino.txt";

//        TratamentoCopos tratamentoCopos = new TratamentoCopos(pathEntrada);
//        tratamentoCopos.tratar(pathSaida);
        TreinamentoCopos treinamentoCopos = new TreinamentoCopos(pathSaida);
        NeuralNetwork rna = treinamentoCopos.treinar();

        rna.save(treino);
        
        
        
        BufferedImage teste = ImageIO.read(new File("teste.jpg"));
        

        ImageRecognitionPlugin imageRecognition = (ImageRecognitionPlugin) rna.getPlugin(ImageRecognitionPlugin.class);

        HashMap<String, Double> output = imageRecognition.recognizeImage(teste);
        System.out.println(output.toString());

    }

}
