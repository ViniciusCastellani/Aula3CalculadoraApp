package com.example.aula3calculadoraapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private TextView textExpressao;
    private TextView textResultado;
    private int contadorOperador = 0;

    private ArrayList<String> listaElementos = new ArrayList<>();

    // StringBuilder = String mutável, quando é modificado não é criado um novo objeto
    private StringBuilder expressao = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textExpressao = findViewById(R.id.TextViewExpressao);
        textResultado = findViewById(R.id.TextViewResultado);
    }

    public void onClickNumero(View v) {
        // v = objeto em que foi clicado, nesse caso o botão.
        Button numeroBT = findViewById(v.getId());
        String numero = numeroBT.getText().toString();

        contadorOperador = 0;

        if ((expressao.length() > 0 && expressao.charAt(expressao.length() - 1) == '.') && numero.equals(".")) {
            return;
        }

        else if (numero.equals(".") && expressao.length() == 0){
            return;
        }

        expressao.append(numero);
        //para colocar no TextView a StringBuilder deve ser convertida em string
        textExpressao.setText(expressao.toString());
    }

    public void onClickOperador(View v) {
        Button operadorBT = findViewById(v.getId());
        String operador = operadorBT.getText().toString();

        if (expressao.length() == 0){
            return;
        }

        contadorOperador++;

        if (contadorOperador >= 2){
            return;
        }

        expressao.append(" ").append(operador).append(" ");
        textExpressao.setText(expressao.toString());
    }

    public void onClickLimpar(View view) {
        //limpa a StringBuilder
        expressao.setLength(0);
        textExpressao.setText("");
        textResultado.setText("");
        listaElementos.clear();
    }

    public void onClickIgual(View v) {
        String expressaoFinal = expressao.toString();

        if (expressaoFinal.length() == 0){
            return;
        }

        String[] elementos = expressaoFinal.split(" ");
        if (elementos.length % 2 == 0 || elementos.length == 1) {
            return;
        }

        listaElementos.addAll(Arrays.asList(elementos));

        double resultadoNum = calcularExpressao(listaElementos);
        //converte double para string
        String resultado = Double.toString(resultadoNum);
        textResultado.setText(resultado);
    }

    private double calcularExpressao(ArrayList<String> elementos) {
        // pega o primeiro número
        double resultado = Double.parseDouble(elementos.get(0));
        double numero = 0;

        for (int i = 1; i < elementos.size(); i += 2) {
            String operador = elementos.get(i);
            if (i + 1 < elementos.size()) {
                numero = Double.parseDouble(elementos.get(i + 1));
            }
            switch (operador) {
                case "+":
                    resultado += numero;
                    break;
                case "-":
                    resultado -= numero;
                    break;
                case "x":
                    resultado *= numero;
                    break;
                case "/":
                    if (numero != 0) {
                        resultado /= numero;
                    } else {
                        // Se o número for zero, retorna um erro
                        return Double.NaN;
                    }
                    break;
            }
        }
        return resultado;
    }
}
