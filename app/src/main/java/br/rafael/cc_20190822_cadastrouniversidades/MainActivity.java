package br.rafael.cc_20190822_cadastrouniversidades;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btToast;
    private Button btDialog;
    private Button btActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Informa para a activity qual será o layout que deverá ser desenhado
        //Para cada objeto(view) do layout um objeto é instanciado da classe
        setContentView(R.layout.activity_main);

        //Busca referencia do objeto criado
        btToast = findViewById(R.id.btToast);
        btDialog = findViewById(R.id.btDialog);
        btActivity = findViewById(R.id.btActivity);

        //Adiciona um evento ao botão e passa a tela como parâmetro
        // A tela deve implementar a interface exigida (View.OnClickListener)
        btToast.setOnClickListener(this);
        btDialog.setOnClickListener(this);
        btActivity.setOnClickListener(this);
    }

    private void exibeToast(String mensagem)
    {
        Toast toast = Toast.makeText(this, mensagem, Toast.LENGTH_SHORT);
        toast.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void exibeDialog(String mensagem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensagem);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                exibeToast("Botão positivo foi clicado");
            }
        });
        builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                exibeToast("Botão negativo foi clicado");
            }
        });
        builder.setNeutralButton("CAFÉ COM LEITE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                exibeToast("Botão atoa foi clicado");
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    /**
     * Método disparado quando é clicado o botão
     */
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btToast:
                exibeToast("Minha mensagem exibida");
                break;
            case R.id.btDialog:
                exibeDialog("Minha mensagem dialog");
                break;
            case R.id.btActivity:
                abreNovaActivity();
                break;
        }
        //
//        if(view.getId() == btToast.getId())
//        {
//            exibeToast("Minha mensagem exibida");
//        }
//        else if(view.getId() == btDialog.getId())
//        {
//            exibeDialog("Minha mensagem dialog");
//        }
    }

    private void abreNovaActivity()
    {
        Intent novaTela = new Intent(this, CadastroActivity.class);
        //
        MeuCadastro meuCadastro = new MeuCadastro();
        meuCadastro.setCodigo(1);
        meuCadastro.setNome("Rafael");
        //
        novaTela.putExtra(ControladorConteudo.NAME_PARAMETRO, meuCadastro);
        startActivityForResult(novaTela, ControladorConteudo.REQUEST_CODE_CADASTRO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case ControladorConteudo.REQUEST_CODE_CADASTRO:
                if(resultCode == RESULT_OK)
                {
                    MeuCadastro meuCadastroAtualizado = (MeuCadastro) data.getSerializableExtra(ControladorConteudo.NAME_PARAMETRO);
                    btActivity.setText(meuCadastroAtualizado.getCodigo() + " " + meuCadastroAtualizado.getNome());
                }
                else if(resultCode == RESULT_CANCELED)
                {
                    exibeToast("Usuário cancelou");
                }
                break;
        }
    }
}
