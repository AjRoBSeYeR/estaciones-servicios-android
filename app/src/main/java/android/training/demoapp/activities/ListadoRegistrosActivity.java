package android.training.demoapp.activities;

import android.app.Activity;
import android.app.AlertDialog;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.training.demoapp.R;
import android.training.demoapp.data.local.database.RegistroDAO;
import android.training.demoapp.activities.baseMenu.BaseNavDrawerActivity;
import android.training.demoapp.pojo.Registro;
import android.training.demoapp.viewModel.RegistroViewModel;
import android.training.demoapp.adapters.RegistroAdapter;
import android.view.View;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ListadoRegistrosActivity extends BaseNavDrawerActivity {
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private static final String LOG_TAG = ListadoRegistrosActivity.class.getSimpleName();

    private Context context = this;
    private Activity activity = (Activity) context;

    private RegistroViewModel mRegistroViewModel;
    private FloatingActionButton addFab;
    private FloatingActionButton deleteFab;

    private RegistroDAO mDao;
    private ArrayList<Registro> Registros;

    private Drawable icon;
    private ColorDrawable background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_registros);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final RegistroAdapter adapter = new RegistroAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRegistroViewModel =  new ViewModelProvider(this).get(RegistroViewModel.class);
        mRegistroViewModel.getAllRegistros().observe(this, new Observer<List<Registro>>() {
            @Override
            public void onChanged(@Nullable final List<Registro> registros) {
                // Update the cached copy of the words in the adapter.
                Registros = (ArrayList<Registro>) registros;
                adapter.setRegistros(Registros);
            }
        });

        addFab = findViewById(R.id.add_fab);
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListadoRegistrosActivity.this, AgregarRegistroActivity.class);
                startActivityForResult(intent,1);

            }
        });
        deleteFab = findViewById(R.id.delete_fab);
        deleteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Borrar todo")
                        .setMessage("Borrar todos los registros?")
                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                mRegistroViewModel.deleteAll();
                            }
                        })
                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });


        //colores swip
        icon = ContextCompat.getDrawable(context,
                R.drawable.ic_add);
       // background = new ColorDrawable(Color.DKGRAY);


// Helper class for creating swipe to dismiss and drag and drop
        // functionality.
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper
                .SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT |
                        ItemTouchHelper.DOWN | ItemTouchHelper.UP,
                ItemTouchHelper.LEFT | ItemTouchHelper.LEFT) {
             /**
             * Defines the drag and drop functionality.
             *
             * @param recyclerView The RecyclerView that contains the list items
             * @param viewHolder The SportsViewHolder that is being moved
             * @param target The SportsViewHolder that you are switching the
             *               original one with.
             * @return true if the item was moved, false otherwise
             */
            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                // Get the from and to positions.
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();

                // Swap the items and notify the adapter.
                Collections.swap(Registros, from, to);
                adapter.notifyItemMoved(from, to);
                return true;
            }

            /**
             * Defines the swipe to dismiss functionality.
             *
             * @param viewHolder The viewholder being swiped.
             * @param direction The direction it is swiped in.
             */
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                 int direction) {
                Intent intent = new Intent(ListadoRegistrosActivity.this, AgregarRegistroActivity.class);
                startActivity(intent);
                // Remove the item from the dataset.
                //Registros.remove(viewHolder.getAdapterPosition());
                // Notify the adapter.
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                View itemView = viewHolder.itemView;
                int backgroundCornerOffset = 20; //so background is behind the rounded corners of itemView

                int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
                int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
                int iconBottom = iconTop + icon.getIntrinsicHeight();

                if (dX < 0) { // Swiping to the left
                    int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
                    int iconRight = itemView.getRight() - iconMargin;
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
//                    background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
//                            itemView.getTop(), itemView.getRight(), itemView.getBottom());
                }

//                if (dX > 0) { // Swiping to the right
//                    int iconLeft = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
//                    int iconRight = itemView.getLeft() + iconMargin;
//                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
//
//                    background.setBounds(itemView.getLeft(), itemView.getTop(),
//                            itemView.getLeft() + ((int) dX) + backgroundCornerOffset, itemView.getBottom());
//                }
//                else if (dX < 0) { // Swiping to the left
//                    int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
//                    int iconRight = itemView.getRight() - iconMargin;
//                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
//
//                    background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
//                            itemView.getTop(), itemView.getRight(), itemView.getBottom());
//                } else { // view is unSwiped
//                    background.setBounds(0, 0, 0, 0);
//                }

                //background.draw(c);
                icon.draw(c);
            }

        });

        // Attach the helper to the RecyclerView
        helper.attachToRecyclerView(recyclerView);


    }//fin on create




    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
//            Registro registro = new Registro(
//                    data.getStringExtra("fecha"),
//                    Double.parseDouble(data.getStringExtra("kmTotales")),
//                    Double.parseDouble(data.getStringExtra("litros")),
//                    Double.parseDouble(data.getStringExtra("euros"))
//                    );
            Registro registro = new Registro();
            registro = data.getParcelableExtra("registro");

            //registro.setFecha((Date)String.valueOf(data.getStringExtra("fecha"))));

            String sDate1=data.getStringExtra("fecha");

            Date date1= null;
            try {
                date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            registro.setFecha(date1);

            mRegistroViewModel.insert(registro);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Salir sin guardar",
                    Toast.LENGTH_LONG).show();
        }

    }

//    public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
//        private RegistroAdapter mAdapter;
//        public SwipeToDeleteCallback(RegistroAdapter adapter) {
//            super(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
//            mAdapter = adapter;
//            icon = ContextCompat. getDrawable (context,
//                    R.drawable. ic_delete_white_36 );
//            fondo = nuevo ColorDrawable (Color. RED );
//        }
//
//        @Override
//        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
//            return false;
//        }
//
//        @Override
//        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//            int position = viewHolder.getAdapterPosition();
//            mAdapter.deleteItem(position);
//        }
//    }



    @Override
    public void onBackPressed() {
        Intent i = new Intent(context, HomeActivity.class);
        startActivity(i);
        finish();
    }

}


