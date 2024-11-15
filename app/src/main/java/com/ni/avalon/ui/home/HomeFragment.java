package com.ni.avalon.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ni.avalon.R;
import com.ni.avalon.adapters.HomeAdapter;
import com.ni.avalon.adapters.PopularAdapters;
import com.ni.avalon.adapters.RecommendedAdapter;
import com.ni.avalon.adapters.ViewAllAdapter;
import com.ni.avalon.databinding.FragmentHomeBinding;
import com.ni.avalon.model.HomeCategory;
import com.ni.avalon.model.PopularModel;
import com.ni.avalon.model.RecommendedModel;
import com.ni.avalon.model.ViewAllModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    ProgressBar progressBar;
    ScrollView scrollView;
    RecyclerView popularRec, homeCatRec, recommendedRec;
    FirebaseFirestore db; // se hace la conexion con la base de datos


    //Items mas populares
    PopularAdapters popularAdapters;
    List<PopularModel> popularModelList;

    // categoria de items
    HomeAdapter homeAdapter;
    List<HomeCategory> categoryList;

    // recomendados
    RecommendedAdapter recommendedAdapter;
    List<RecommendedModel> recommendedModelList;

    EditText barraBusqueda;
    private List<ViewAllModel> viewAllModelList;
    private RecyclerView recyclerViewBuscar;
    private ViewAllAdapter viewAllAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_home, container, false);
        db = FirebaseFirestore.getInstance();

        popularRec = root.findViewById(R.id.pop_rec); //lo asociamos con el id del list view correspondiente a populares
        homeCatRec = root.findViewById(R.id.explore_rec);
        recommendedRec = root.findViewById(R.id.recommended_rec);
        scrollView = root.findViewById(R.id.scroll_view_home);
        progressBar = root.findViewById(R.id.progressbarMenu);

        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        // items mas populares
        popularRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        popularModelList = new ArrayList<>();
        popularAdapters = new PopularAdapters(getActivity(),popularModelList);
        popularRec.setAdapter(popularAdapters);


        // este codigo te lo genera el apartado cloud firestore cuando vas a importar las librerias de cloud store
        db.collection("PopularProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PopularModel popularModel = document.toObject(PopularModel.class);
                                popularModelList.add(popularModel);
                                popularAdapters.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        // catalogo
        homeCatRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categoryList = new ArrayList<>();
        homeAdapter = new HomeAdapter(getActivity(),categoryList);
        homeCatRec.setAdapter(homeAdapter);


        // este codigo te lo genera el apartado cloud firestore cuando vas a importar las librerias de cloud store
        db.collection("HomeCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HomeCategory homeCategory = document.toObject(HomeCategory.class);
                                categoryList.add(homeCategory);
                                homeAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        // recomendado
        recommendedRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        recommendedModelList = new ArrayList<>();
        recommendedAdapter = new RecommendedAdapter(getActivity(),recommendedModelList);
        recommendedRec.setAdapter(recommendedAdapter);


        // este codigo te lo genera el apartado cloud firestore cuando vas a importar las librerias de cloud store
        db.collection("Recommended")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                RecommendedModel recommendedModel = document.toObject(RecommendedModel.class);
                                recommendedModelList.add(recommendedModel);
                                recommendedAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        // barra de busqueda
        barraBusqueda = root.findViewById(R.id.barraBusqueda);
        recyclerViewBuscar = root.findViewById(R.id.res_busqueda);
        recyclerViewBuscar.setLayoutManager(new LinearLayoutManager(getContext()));
        viewAllModelList = new ArrayList<>();
        viewAllAdapter = new ViewAllAdapter(getContext(),viewAllModelList);
        recyclerViewBuscar.setAdapter(viewAllAdapter);
        recyclerViewBuscar.setHasFixedSize(true);
        barraBusqueda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().isEmpty()){
                    viewAllModelList.clear();
                    viewAllAdapter.notifyDataSetChanged();
                } else {
                    searchProduct(s.toString());
                }
            }
        });

        return root;
    }

    private void searchProduct(String nombre) {
        if (!nombre.isEmpty()) { // Cambia la condición para verificar que tipo no está vacío
            db.collection("AllProducts").whereEqualTo("nombre", nombre).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                viewAllModelList.clear(); // Limpia la lista para mostrar solo resultados nuevos
                                for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                    ViewAllModel viewAllModel = doc.toObject(ViewAllModel.class);
                                    viewAllModelList.add(viewAllModel);
                                }
                                viewAllAdapter.notifyDataSetChanged(); // Notifica cambios al adaptador
                            } else {
                                Toast.makeText(getContext(), "No se encontraron productos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

}