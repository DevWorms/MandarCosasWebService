package com.salvador.devworms.buscador;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    Button btnBuscar;
    Button btnenconar;
    TextView txtRuta;
    TextView txtContenido;
    String ubicacion;
    String tamano;
    String nombre;

    String respsuesta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBuscar=(Button)findViewById(R.id.mandar);
        btnenconar=(Button)findViewById(R.id.encontar);
        txtRuta=(TextView)findViewById(R.id.ruta);
        txtContenido=(TextView)findViewById(R.id.contenido);

        btnBuscar.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View view){

                    new Thread()
                    {
                        public void run()
                        {
                            try
                            {

                            /*

                               HttpClient client = new DefaultHttpClient();
                                HttpPost post = new HttpPost("http://hurryapp.devworms.com/subir.php");
                               /* List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                                pairs.add(new BasicNameValuePair("numero","1"));
                                pairs.add(new BasicNameValuePair("name", nombre));
                                pairs.add(new BasicNameValuePair("documento", ubicacion));


                                post.setEntity(new UrlEncodedFormEntity(pairs));
                                post.addHeader("Accept", "application/json");


                                List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                                pairs.add(new BasicNameValuePair("numero","111"));
                                pairs.add(new BasicNameValuePair("documento", new FileEntity(new File(ubicacion, nombre), "").toString()));
                                post.setEntity(new UrlEncodedFormEntity(pairs));



                                HttpResponse response = client.execute(post);
*/
                                SyncHttpClient client = new SyncHttpClient();
                                RequestParams params = new RequestParams();
                                params.put("numero", "1");
                                params.put("documento", new File(ubicacion));

                                client.post("tu server", params, new TextHttpResponseHandler() {
                                    @Override
                                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {

                                    }

                                    @Override
                                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                                        respsuesta=responseString;
                                        Message mesa=new Message();
                                        vistaHandler.sendMessage(mesa);
                                    }


                                });
                              /*  BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                                StringBuffer sb = new StringBuffer("");
                                String line = "";
                                String NL = System.getProperty("line.separator");
                                while ((line = in.readLine()) != null) {
                                    sb.append(line + NL);
                                }
                                in.close();

                                //Respuesta en formato String
                                 respsuesta = sb.toString();
                                Message mesa=new Message();

                               vistaHandler.sendMessage(mesa);
*/
                            }
                            catch (Exception ex)
                            {
                                Message mesa=new Message();
                                respsuesta=ex.toString();
                                vistaHandler.sendMessage(mesa);
                            }
                        }

                    }.start();





            }

        });
        btnenconar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){


                irExplorador();

            }

        });
        try {
            Intent i=getIntent();
            ubicacion=i.getExtras().getString("ubicacion");
            nombre=i.getExtras().getString("nombre");
            tamano=i.getExtras().getString("tamano");

        }catch (Exception e){

        }
        if(ubicacion!="")
            Toast.makeText(getApplicationContext(), "Archivo cargado.",
                    Toast.LENGTH_SHORT).show();
            abrirArchivo();

    }



    Handler vistaHandler = new Handler() {
        public void handleMessage(Message msg) {
            Toast.makeText(getApplicationContext(),respsuesta,
                    Toast.LENGTH_SHORT).show();

        }

    };



    private void abrirArchivo(){
        try{
            txtRuta.setText(ubicacion);
            File f= new File(ubicacion);
            if(f==null)
                txtContenido.setText("archivo no valido");
           /* FileReader fr = new FileReader(f);
            BufferedReader br=new BufferedReader(fr);
            String texto=br.readLine();
            String aux="";
            while(texto!=null){
                aux=aux+texto;
                texto=br.readLine();

            }
            txtContenido.setText(aux);
            br.close();*/


        }catch (Exception e){}
    }
    public void irExplorador(){
        Intent i= new Intent(MainActivity.this,Exp.class);
        startActivity(i);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
