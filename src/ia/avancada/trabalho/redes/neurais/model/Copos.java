/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ia.avancada.trabalho.redes.neurais.model;

import ia.avancada.trabalho.redes.neurais.util.Log;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import org.neuroph.imgrec.FractionRgbData;

/**
 *
 * @author Arthur
 */
public class Copos {

    private final Map<String, BufferedImage> imagemCopo;
    private final String pastaDasImagens;
    private final String nomeCopo;

    public Copos(String nomeCopo, String pastaDasImagens) {
        this.imagemCopo = new HashMap<>();
        this.pastaDasImagens = pastaDasImagens;
        this.nomeCopo = nomeCopo;
    }

    public Map<String, BufferedImage> lerImanges() throws IOException {
        
        Log.Debug(getClass().getName(), "Processando copo " + nomeCopo);
        
        File folder = new File(this.pastaDasImagens);
        File[] listOfFiles = folder.listFiles();
        int count = 0;
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                count++;
                BufferedImage copoBufferedImage = ImageIO.read(listOfFile);
//                FractionRgbData copoFraction = new FractionRgbData(copoBufferedImage);
                imagemCopo.put(nomeCopo+count, copoBufferedImage);
            }
        }
        
        return imagemCopo;
    }
    
    

}
