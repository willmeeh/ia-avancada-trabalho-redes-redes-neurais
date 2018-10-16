/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ia.avancada.trabalho.redes.neurais.model;

import ia.avancada.trabalho.redes.neurais.ArquivoConfiguracaoRede;
import ia.avancada.trabalho.redes.neurais.util.Log;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Arthur
 */
public class InterfaceCopos implements ArquivoConfiguracaoRede {

    protected final Map<String, BufferedImage> pastasCopos;
    protected final LinkedList<String> coposLabel;
    protected final String pasta;

    public InterfaceCopos(String pasta) {
        this.coposLabel = new LinkedList<>();
        this.pastasCopos = new HashMap<>();
        this.pasta = pasta;
        lerCopos();
    }

    private void lerCopos() {
        if (pastasCopos == null || pastasCopos.isEmpty()) {
            File folder = new File(this.pasta);
            File[] listOfDirectorys = folder.listFiles();

            Copos copos;

            Log.Debug(getClass().getName(), "Lendo arquivos");
            for (File listOfFile : listOfDirectorys) {
                if (listOfFile.isDirectory()) {
                    coposLabel.add(listOfFile.getName());
                    copos = new Copos(listOfFile.getName(), listOfFile.getPath() + "/");
                    try {
                        pastasCopos.putAll(copos.lerImanges());
                    } catch (IOException ex) {
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            Log.Debug(getClass().getName(), "Total de copos: " + pastasCopos.size());
        }
    }
}
