/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ia.avancada.trabalho.redes.neurais;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.learning.SupervisedLearning;
import org.neuroph.imgrec.ColorMode;
import org.neuroph.imgrec.FractionRgbData;
import org.neuroph.imgrec.ImageRecognitionHelper;
import org.neuroph.imgrec.ImageRecognitionPlugin;
import org.neuroph.imgrec.image.Dimension;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.nnet.learning.SupervisedHebbianLearning;
import org.neuroph.util.TransferFunctionType;

/**
 *
 * @author will
 */
public class IaAvancadaTrabalhoRedesNeurais {

    /**
     * @param args the command line arguments
     */
    public static void main2(String[] args) {
        
        
        
        
        
        
        
        
        

//        File dato = new File("/home/will/Downloads/lol.jpg");
//        File rnaPath = new File("/home/will/NetBeansProjects/ia-avancada-trabalho-redes-neurais/RNA.nnet");
//
//        BufferedImage image = null;
//        try {
//            image = ImageIO.read(dato);
//
//            FileInputStream fis = null;
//
//            fis = new FileInputStream(rnaPath);
////            NeuralNetwork rna = NeuralNetwork.load(fis);
            NeuralNetwork rna = criarRna(); 
            treinarRede(rna);
//            
//            
//
//        } catch (IOException ex) {
//            Logger.getLogger(IaAvancadaTrabalhoRedesNeurais.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    private static NeuralNetwork criarRna() {

        List<String> imageLabels = new ArrayList<>();
        imageLabels.add("mug");
        imageLabels.add("pilsner");

        List<Integer> layersNeuronsCount = new ArrayList<>();
        Dimension dimension = new Dimension(50, 100);
        NeuralNetwork rna = ImageRecognitionHelper.createNewNeuralNetwork(
                "rna",
                dimension,
                ColorMode.BLACK_AND_WHITE,
                imageLabels,
                layersNeuronsCount,
                TransferFunctionType.SIGMOID
        );

        return rna;
    }

    private static void treinarRede(NeuralNetwork rna) {
        try {
            
            BufferedImage pilsnerImage = ImageIO.read(new File("copos/pilsner50x100_binarizado/image.jpg"));
            BufferedImage pilsnerImage2 = ImageIO.read(new File("copos/pilsner50x100_binarizado/image2.jpg"));
            BufferedImage pilsnerImage3 = ImageIO.read(new File("copos/pilsner50x100_binarizado/image3.jpg"));
            BufferedImage pilsnerImage4 = ImageIO.read(new File("copos/pilsner50x100_binarizado/image4.jpg"));
            BufferedImage mugImage = ImageIO.read(new File("copos/mug50x100_binarizado/image.jpg"));
            
            Map<String, FractionRgbData> hm = new HashMap<String, FractionRgbData>();
            
            FractionRgbData pilsnerRgb = new FractionRgbData(pilsnerImage);
            FractionRgbData pilsnerRgb2 = new FractionRgbData(pilsnerImage2);
            FractionRgbData pilsnerRgb3 = new FractionRgbData(pilsnerImage3);
            FractionRgbData pilsnerRgb4 = new FractionRgbData(pilsnerImage4);
            FractionRgbData mugRgb = new FractionRgbData(mugImage);
            
            hm.put("mug", mugRgb);
            hm.put("pilsner", pilsnerRgb);
            hm.put("pilsner", pilsnerRgb2);
            hm.put("pilsner", pilsnerRgb3);
            hm.put("pilsner", pilsnerRgb4);
            
            List<String> imageLabels = new ArrayList<>();
            imageLabels.add("mug");
            imageLabels.add("pilsner");

            

            DataSet dt = ImageRecognitionHelper.createBlackAndWhiteTrainingSet(imageLabels, hm);
            
//            Dimension samplingResolution = new Dimension(50, 100);
//            DataSet dt = ImageRecognitionHelper.createImageDataSetFromFile(
//                "copos/pilsner50x100_binarizado",
//                imageLabels,
//                null,
//                ColorMode.BLACK_AND_WHITE,
//                samplingResolution,
//                "pilsner",
//                1
//            );

//            System.out.println(dt.toString());
            System.out.println("Inicio do treino");
            
            
//            SupervisedLearning learningRule = new SupervisedHebbianLearning();
            SupervisedLearning learningRule = new SupervisedLearning() {
                @Override
                protected void calculateWeightChanges(double[] outputError) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
//            MomentumBackpropagation learningRule = (MomentumBackpropagation) rna.getLearningRule();
            learningRule.setLearningRate(0.2);
            learningRule.setMaxError(0.00000000001);
            learningRule.setMaxIterations(500000000);
            rna.learn(dt);
//            TimeUnit.MILLISECONDS.sleep(1000);
            rna.stopLearning();
            System.out.println("Fim do treino");
            
            ImageRecognitionPlugin imageRecognition = (ImageRecognitionPlugin) rna.getPlugin(ImageRecognitionPlugin.class);
            
            

            HashMap<String, Double> output = imageRecognition.recognizeImage(pilsnerImage);
            System.out.println(output.toString());
        } catch (IOException ex) {
            Logger.getLogger(IaAvancadaTrabalhoRedesNeurais.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } 

    }

}
