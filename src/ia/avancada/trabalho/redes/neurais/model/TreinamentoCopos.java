/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ia.avancada.trabalho.redes.neurais.model;

import static ia.avancada.trabalho.redes.neurais.ArquivoConfiguracaoRede.LEARNING_RATE;
import static ia.avancada.trabalho.redes.neurais.ArquivoConfiguracaoRede.MAX_ERROR;
import static ia.avancada.trabalho.redes.neurais.ArquivoConfiguracaoRede.MAX_ITERATIONS;
import ia.avancada.trabalho.redes.neurais.util.Log;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.imgrec.ColorMode;
import org.neuroph.imgrec.FractionRgbData;
import org.neuroph.imgrec.ImageRecognitionHelper;
import org.neuroph.imgrec.image.Dimension;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;

/**
 *
 * @author Arthur
 */
public class TreinamentoCopos extends InterfaceCopos {

    public TreinamentoCopos(String pasta) {
        super(pasta);
    }

    private Map<String, FractionRgbData> castHashMap(Map<String, BufferedImage> pastasCopos) {
        Map<String, FractionRgbData> casted = new HashMap<>();

        pastasCopos.entrySet().forEach((entry) -> {
            casted.put(entry.getKey(), new FractionRgbData(entry.getValue()));
        });
        return casted;
    }

    public NeuralNetwork treinar() {
        Dimension dimension = new Dimension(50, 100);
        List<Integer> layersNeuronsCount = new ArrayList<>();
        NeuralNetwork rna = ImageRecognitionHelper.createNewNeuralNetwork(
                "rna",
                dimension,
                ColorMode.BLACK_AND_WHITE,
                coposLabel,
                layersNeuronsCount,
                TransferFunctionType.SIGMOID
        );
        Log.Debug(getClass().getName(), "Iniciando treinamento da rede");

        Map<String, FractionRgbData> map = castHashMap(pastasCopos);
        for (Map.Entry<String, FractionRgbData> entry : map.entrySet()) {
            String key = entry.getKey();
            System.out.println(key);
        }

        for (String string : coposLabel) {
            System.out.println(string);
        }

        DataSet dt = ImageRecognitionHelper.createBlackAndWhiteTrainingSet(coposLabel, map);
        
        dt.save("copos/dt.tset");

        MomentumBackpropagation learningRule = new MomentumBackpropagation();

        Log.Debug(getClass().getName(), "Dataset ok");

        learningRule.setLearningRate(LEARNING_RATE);
        learningRule.setMaxError(MAX_ERROR);
        learningRule.setMaxIterations(MAX_ITERATIONS);
        Log.Debug(getClass().getName(), "Learning start");
        rna.learn(dt, learningRule);
        Log.Debug(getClass().getName(), "Learning stop");
        rna.stopLearning();
        rna.save("copos/rna.rna");
        return rna;
    }

}
