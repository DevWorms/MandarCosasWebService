package com.salvador.devworms.buscador;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by salvador on 09/12/2015.
 */
public class Exp extends ListActivity {
    private List<String>listaNombresArchivos;
    private List<String>listaRutasArchivos;
    private ArrayAdapter<String>adaptador;
    private String directprioRaiz;
    private TextView carpetaActual;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorador);

        carpetaActual=(TextView)findViewById(R.id.rutaAcual);
        directprioRaiz= Environment.getExternalStorageDirectory().getPath();
        verArchivosDirectorio(directprioRaiz);
    }
    public void verArchivosDirectorio(String rutaDrectorio){
        carpetaActual.setText("Esta en: " + rutaDrectorio);
        listaNombresArchivos= new ArrayList<String>();
        listaRutasArchivos= new ArrayList<String>();
        File directorioActual=new File(rutaDrectorio);
        File[] listaArchivos= directorioActual.listFiles();
        int x=0;
        if(!rutaDrectorio.equals(directprioRaiz)){
            listaNombresArchivos.add("../");
            listaRutasArchivos.add(directorioActual.getParent());
            x=1;
        }
        for(File archivo : listaArchivos){
            listaRutasArchivos.add(archivo.getPath());
        }
        Collections.sort(listaRutasArchivos,String.CASE_INSENSITIVE_ORDER);

        for(int i=x;i<listaRutasArchivos.size();i++){
            File archivo=new File(listaRutasArchivos.get(i));
            if(archivo.isFile()){
                listaNombresArchivos.add(archivo.getName());
            }else {
                listaNombresArchivos.add("/"+archivo.getName());
            }

        }
        if(listaArchivos.length<1){
            listaNombresArchivos.add("No hay archivos");
            listaRutasArchivos.add(rutaDrectorio);
        }
        adaptador= new ArrayAdapter<String>(this,R.layout.activity_explorador,R.id.empty,listaNombresArchivos);
        setListAdapter(adaptador);
    }


    protected void onListItemClick(ListView l,View v,int position, long id){
        File archivo=new File(listaRutasArchivos.get(position));
        if(archivo.isFile()){
            String ubicacion=archivo.getPath();
            String nombre=archivo.getName();
            byte[] tamano= new byte[(int) archivo.length()];
            Intent i= new Intent(this,MainActivity.class);
            i.putExtra("ubicacion",ubicacion);
            i.putExtra("nombre",nombre);
            i.putExtra("tamano",tamano);

            startActivity(i);
        }else{
            verArchivosDirectorio(listaRutasArchivos.get(position));
        }


    }
}
