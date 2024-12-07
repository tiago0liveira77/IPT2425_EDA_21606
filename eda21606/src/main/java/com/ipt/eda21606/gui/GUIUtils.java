/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipt.eda21606.gui;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class GUIUtils {

    //Metodo para executar funçoes em background e devolver algo
    public static <T> T executeLongTaskWithResult(JFrame parentFrame, String message, Supplier<T> task) {
        // Cria o diálogo de loading
        LoadingDialog loadingDialog = new LoadingDialog(parentFrame, message);

        final AtomicReference<T> result = new AtomicReference<>();

        // Tarefa que será executada em segundo plano
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Executar a tarefa fornecida e armazenar o resultado
                result.set(task.get());
                return null;
            }

            @Override
            protected void done() {
                // Fechar o diálogo ao finalizar
                loadingDialog.dispose();
            }
        };

        // Exibir o diálogo de loading em outra thread para evitar bloqueio
        SwingUtilities.invokeLater(() -> {
            loadingDialog.setVisible(true);
        });

        // Iniciar a tarefa
        worker.execute();

        // Aguardar o resultado
        try {
            worker.get(); // Garante que a tarefa termine antes de acessar o resultado
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return result.get();
    }

     //Metodo para executar funçoes em background
    public static void executeLongTask(JFrame parentFrame, String message, Runnable task) {
        // Cria o JDialog de loading
        LoadingDialog loadingDialog = new LoadingDialog(parentFrame, message);

        // Executa a tarefa em uma thread separada
        new Thread(() -> {
            try {
                // Executa a tarefa em background
                task.run();
            } catch (Exception e) {
                e.printStackTrace(); // Log de erro (pode ser substituído por logger)
            } finally {
                // Após terminar a tarefa, remove o dialog em EDT
                SwingUtilities.invokeLater(loadingDialog::dispose);
            }
        }).start();

        // Mostra o diálogo de carregamento (bloqueia a interface até ser descartado)
        loadingDialog.setVisible(true);
    }

}
