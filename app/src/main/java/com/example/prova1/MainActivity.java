package com.example.prova1;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText edAltura;
    private EditText edPeso;
    private RadioGroup radioGroupSexo;

    private double imc;
    private String situacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edAltura = findViewById(R.id.edAltura);
        edPeso = findViewById(R.id.edPeso);
        radioGroupSexo = findViewById(R.id.radioGroupSexo);
    }

    public void calcular(View view){

        if (edAltura.getText().toString().isEmpty() || edPeso.getText().toString().isEmpty()) {
            Toast.makeText(this,R.string.msg_dados_invalidos, Toast.LENGTH_SHORT).show();
            edAltura.setText("");
            edPeso.setText("");
            radioGroupSexo.clearCheck();
            return;
        }

        double altura = Double.parseDouble(edAltura.getText().toString()) / 100;
        double peso = Double.parseDouble(edPeso.getText().toString());

        double min = 18.5;
        double max = 24.9;
        double pesoMin = min * Math.pow(altura, 2);
        double pesoMax = max * Math.pow(altura, 2);

        int sexoId = radioGroupSexo.getCheckedRadioButtonId();
        RadioButton botaoSelecionado = findViewById(sexoId);
        String sexo = botaoSelecionado.getText().toString();

        if(dadosValidos(altura, peso, sexoId)){
            imc = peso / (Math.pow(altura, 2));

            if (sexo.equals(getString(R.string.sexo_masculino))) {
                if (imc < 19.1) {
                    situacao = getString(R.string.situacao_abaixo_peso);
                } else if (imc >= 19.1 && imc <= 25.8) {
                    situacao = getString(R.string.situacao_peso_ideal);
                } else if (imc >= 25.9 && imc <= 27.3) {
                    situacao = getString(R.string.situacao_pouco_acima);
                } else if (imc >= 27.4 && imc <= 32.3) {
                    situacao = getString(R.string.situacao_acima_peso);
                } else {
                    situacao = getString(R.string.situacao_obesidade);
                }
            } else {
                if (imc < 20.7) {
                    situacao = getString(R.string.situacao_abaixo_peso);
                } else if (imc >= 20.7 && imc <= 26.4) {
                    situacao = getString(R.string.situacao_peso_ideal);
                } else if (imc >= 26.5 && imc <= 27.8) {
                    situacao = getString(R.string.situacao_pouco_acima);
                } else if (imc >= 27.9 && imc <= 31.1) {
                    situacao = getString(R.string.situacao_acima_peso);
                } else {
                    situacao = getString(R.string.situacao_obesidade);
                }
            }
            mostrarResultado(imc, situacao, pesoMin, pesoMax);
        }


    }

    public void mostrarResultado(Double imc, String situacao, double pesoMin, double pesoMax){
        View dialogInfo = getLayoutInflater().inflate(R.layout.dialog_info, null);
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setView(dialogInfo);

        TextView textResultado = dialogInfo.findViewById(R.id.textResultado);
        TextView textSituacao = dialogInfo.findViewById(R.id.textSituacao);
        TextView textFaixaIdeal = dialogInfo.findViewById(R.id.textFaixaIdeal);

        textResultado.setText(getString(R.string.label_seu_imc) + " " + String.format("%.2f", imc));
        textSituacao.setText(getString(R.string.label_situacao) + " " + situacao);
        textFaixaIdeal.setText(getString(R.string.label_faixa_ideal) + " " + String.format("%.2f", pesoMin) + " ~ " + String.format("%.2f", pesoMax));



        bld.setPositiveButton(R.string.botao_voltar, null);

        AlertDialog dialog = bld.create();
        dialog.show();
    }

    public boolean dadosValidos(Double altura, Double peso, int sexoId){
        if (altura <= 0 || peso <= 0 || altura == null || peso == null || sexoId == -1) {
            Toast.makeText(this,R.string.msg_dados_invalidos, Toast.LENGTH_SHORT).show();
            edAltura.setText("");
            edPeso.setText("");
            radioGroupSexo.clearCheck();
            return false;
        } else {
            return true;
        }
    }

    public void limpar(View view){
        edAltura.setText("");
        edPeso.setText("");
        radioGroupSexo.clearCheck();
    }
}