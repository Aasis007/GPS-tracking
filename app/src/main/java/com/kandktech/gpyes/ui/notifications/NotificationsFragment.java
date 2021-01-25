package com.kandktech.gpyes.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kandktech.gpyes.Adapter.NotificationsfragmentAdapter;
import com.kandktech.gpyes.Model.NotificationsModel;
import com.kandktech.gpyes.R;
import com.kandktech.gpyes.Retrofit.ApiInterface;
import com.kandktech.gpyes.Retrofit.RetrofitClient;
import com.kandktech.gpyes.Utls.SessionManagement;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NotificationsFragment extends Fragment {
    private final Retrofit retrofit = RetrofitClient.getRetrofit();
    private final ApiInterface apiInterface =retrofit.create(ApiInterface.class);
    RecyclerView noti_rv;
    View view;
    NotificationsfragmentAdapter notificationsfragmentAdapter;
    NotificationsModel notificationsModels;




    public NotificationsFragment() {
        //required empty constructor

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notifications,container,false);


        SessionManagement sessionManagement = new SessionManagement(getActivity());

        int CID = sessionManagement.getCompid();
        Log.d("CPid","company"+CID);

        noti_rv = view.findViewById(R.id.notifications_rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        noti_rv.setLayoutManager(linearLayoutManager);
        noti_rv.setHasFixedSize(true);
        Log.d("cid","cid"+CID);

        //get notificaitions from server
        apiInterface.getnotification(CID).enqueue(new Callback<NotificationsModel>() {
            @Override
            public void onResponse(@NotNull Call<NotificationsModel> call, @NotNull Response<NotificationsModel> response) {


                        assert response.body() != null;
                        if (response.isSuccessful()) {

                            String resp = response.body().toString();
                            Log.d("response:","notiresp"+resp);


                            notificationsfragmentAdapter = new NotificationsfragmentAdapter(getContext(),response.body().getNotification_data());

                            noti_rv.setAdapter(notificationsfragmentAdapter);


                            notificationsfragmentAdapter.notifyDataSetChanged();


                            Log.d("notifiation","noti"+response.body().getNotification_data().get(0).getTitle());


                        }
            }

            @Override
            public void onFailure(Call<NotificationsModel> call, Throwable t) {

            }
        });


        return view;

    }
}