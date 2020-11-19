/*
package com.example.noti_net;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ImagePaint
{
    private Bitmap[] imageBitMap;
    private Bitmap[][] imageBitmaps;
    private ImageView[] image;
    private ImageView[][] imageDoble;
    private int principal;
    private View view;
    private LinearLayout linearLayout;
    private GridLayout gridLayout;
    private String routePromotion;
    private String messageServer;
    private int statusResponse;
    private RequestQueue queue;
    private float totalImage;
    private String typeImage;
    private Activity activity;
    private JSONObject[] structService;
    private JSONObject[] info;
    private int column;
    private ImageView local, marca;
    Fragment fragment;


    public ImagePaint(GridLayout gridLayout, int column, Activity activity, String typeImage)
    {
        this.imageBitMap = null;
        this.image = null;
        this.routePromotion = routePromotion;
        this.gridLayout = gridLayout;
        this.activity = activity;
        this.typeImage = typeImage;
        this.column = column;
        queue = Volley.newRequestQueue(activity);
    }

    public void getImages(String routeServer)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, routeServer, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try
                        {
                            response.getJSONArray("estructura");
                            assignPropertyImage(response);
                            if(getLinearLayout() != null)
                                paintPictureLayout();
                            else
                                paintPictureGrid();

                            lottie.dismissLoadingDialogLottie(activity);
                        }
                        catch (JSONException e)
                        {
                            Toast.makeText(getActivity(), "No hay coincidencias con lo que busca",
                                    Toast.LENGTH_LONG).show();
                            lottie.dismissLoadingDialogLottie(activity);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                try{
                    lottie.dismissLoadingDialogLottie(activity);
                }
                catch(Exception e){
                    System.out.println(e);
                }
                System.out.println(error);
            }
        })
        {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                statusResponse = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(jsonObjectRequest);
    }

    private void assignPropertyImage(JSONObject response)
    {
        try
        {
            messageServer = response.getString("mensaje");
            System.out.println("messageServer: " + messageServer);
            setTotalImage(response.getJSONArray("estructura").length());
            setInfo(new JSONObject[(int) getTotalImage()]);
            setStructService(new JSONObject[(int) getTotalImage()]);
            setImageBitMap(new Bitmap[(int) getTotalImage()]);
            setImage(new ImageView[(int) getTotalImage()]);
            if(messageServer.equals("Doble"))
            {
                setImageBitmaps(new Bitmap[(int) getTotalImage()][]);
                setImageDoble(new ImageView[(int) getTotalImage()][]);
            }
            if(getLinearLayout() != null)
                for (int x = 0; x < getTotalImage(); x++)
                {
                    getStructService()[x] = (JSONObject) response.getJSONArray("estructura").get(x);
                    getInfo()[x] = (getStructService()[x].getJSONObject("info"));
                }
            else
                for (int x = 0; x < getTotalImage(); x++)
                    getStructService()[x] = (JSONObject) response.getJSONArray("estructura").get(x);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }


    public void paintPictureGrid(String routeServer)
    {
        lottie.showLoadingDialogLottie(activity);
        getImages(routeServer);
    }

    private void paintPictureGrid()
    {
        try
        {
            getGridLayout().removeAllViews();
            getGridLayout().setColumnCount(getColumn());
            getGridLayout().setRowCount((int)Math.ceil(getTotalImage() / getColumn()));
            if(getGridLayout().getId()==R.id.gridBloopiMarket)
                getGridLayout().setRowCount((int)Math.ceil(getTotalImage() / getColumn())*2);
            byte[] bytes;
            GridLayout.LayoutParams paramGridLayout;
            int i=0, j=1;
            for(int x = 0, c = 0, r = 0; x < getTotalImage(); x++, c++)
            {
                paramGridLayout = new GridLayout.LayoutParams();
                if(c == getColumn())
                {
                    c = 0;
                    ++r;
                    if(getGridLayout().getId()==R.id.gridBloopiMarket)
                        r++;
                    */
/*if((x + getColumn()) > (int)getTotalImage())
                    {
                        setColumn((int)getTotalImage() - x);
                        //getGridLayout().setColumnCount(getColumn());
                    }*//*

                }
                paramGridLayout.height = 300;
                paramGridLayout.width = 300;
                paramGridLayout.leftMargin = 10;
                paramGridLayout.rightMargin = 10;
                paramGridLayout.topMargin = 5;
                paramGridLayout.columnSpec = GridLayout.spec(c);
                paramGridLayout.rowSpec = GridLayout.spec(r);

                if(getGridLayout().getId()==R.id.gridBloopiMarket){
                    getInfo()[x] = (getStructService()[x].getJSONObject("info"));
                    paramGridLayout.leftMargin = 80;
                    paramGridLayout.rightMargin = 80;
                    paramGridLayout.topMargin = 50;
                    paramGridLayout.height = 200;
                    paramGridLayout.width = 200;
                    paramGridLayout.columnSpec = GridLayout.spec(c);
                    paramGridLayout.rowSpec = GridLayout.spec(r);

                    TextView categoria = new TextView(getActivity());
                    categoria.setText(getInfo()[x].getString("categoria"));
                    categoria.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    categoria.setTextSize(2,12);
                    categoria.setTextColor(getActivity().getColor(R.color.c15));
                    categoria.setTypeface(Typeface.DEFAULT_BOLD);

                    GridLayout.LayoutParams paramsCategoria = new GridLayout.LayoutParams();
                    paramsCategoria.leftMargin = 30;
                    paramsCategoria.rightMargin = 30;
                    paramsCategoria.topMargin = 50;
                    paramsCategoria.height = GridLayout.LayoutParams.WRAP_CONTENT;
                    paramsCategoria.width = 250;
                    paramsCategoria.columnSpec = GridLayout.spec(c);
                    paramsCategoria.rowSpec = GridLayout.spec(r+1);
                    categoria.setLayoutParams(paramsCategoria);
                    getGridLayout().addView(categoria);

                }
                getImage()[x] = new ImageView(getActivity());
                getImage()[x].setLayoutParams(paramGridLayout);
                bytes = Base64.decode(getStructService()[x].getString("img").getBytes(), Base64.DEFAULT);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = false;
                getImageBitMap()[x] = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                getImage()[x].setImageBitmap(getImageBitMap()[x]);

                if(getGridLayout().getId()==R.id.gridMarketActivity)
                {
                    View row = fragment.getLayoutInflater().inflate(R.layout.celda_market_productos,null);//vi.inflate(R.layout.celda_market_productos, null, false);
                    ImageView image = row.findViewById(R.id.market_product_image);
                    image.setImageBitmap(getImageBitMap()[x]);
                    image.setLayoutParams(new ConstraintLayout.LayoutParams((getGridLayout().getWidth())/2-40,(getGridLayout().getWidth())/2-40));
                    getImage()[x] = image;
                    TextView textView = row.findViewById(R.id.market_product_textPromo);
                    textView.setVisibility(View.INVISIBLE);
                    TextView precio = row.findViewById(R.id.market_product_precio);
                    precio.setText("$" +getInfo()[x].getString("precio"));
                    GridLayout.LayoutParams paramsCategoria = new GridLayout.LayoutParams();
                    if(c==0)
                        paramsCategoria.rightMargin = 20;
                    else
                        paramsCategoria.leftMargin = 20;
                    paramsCategoria.topMargin = 20;
                    paramsCategoria.height = GridLayout.LayoutParams.WRAP_CONTENT;
                    paramsCategoria.width = GridLayout.LayoutParams.WRAP_CONTENT;
                    paramsCategoria.columnSpec = GridLayout.spec(c);
                    paramsCategoria.rowSpec = GridLayout.spec(r);
                    row.setLayoutParams(paramsCategoria);

                    getGridLayout().addView(row);
                }else
                    getGridLayout().addView(getImage()[x]);

            }
            createListenerView();
        }
        catch (JSONException e)
        {
            System.out.println("Hey!!");
        }
        catch (Exception e)
        {
            System.out.println();
        }
    }

    private void createListenerView()
    {
        */
/*for (int x = 0; x < getImage().length; x++)
        {
        }*//*

    }
    
}
*/
