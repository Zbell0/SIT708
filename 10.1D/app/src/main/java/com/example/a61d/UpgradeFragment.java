package com.example.a61d;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;
import org.json.JSONArray;
import org.json.JSONObject;

public class UpgradeFragment extends Fragment {

    private static final int LOAD_PAY_REQUEST = 1001;
    private PaymentsClient walletClient;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater i,
                             @Nullable ViewGroup c, @Nullable Bundle s) {

        View v = i.inflate(R.layout.fragment_upgrade, c, false);

        walletClient = Wallet.getPaymentsClient(
                requireContext(),
                new Wallet.WalletOptions.Builder()
                        .setEnvironment(WalletConstants.ENVIRONMENT_TEST)
                        .build());

        v.findViewById(R.id.upgradeBackBtn).setOnClickListener(
                _v -> requireActivity().getSupportFragmentManager().popBackStack());

        setClick(v, R.id.btnStarter);
        setClick(v, R.id.btnIntermediate);
        setClick(v, R.id.btnAdvanced);

        return v;
    }


    private void setClick(View root, int id) {
        root.findViewById(id).setOnClickListener(_v -> showPaySheet());
    }


    private void showPaySheet() {
        try {

            JSONObject reqJson = new JSONObject()
                    .put("apiVersion", 2).put("apiVersionMinor", 0)
                    .put("allowedPaymentMethods", new JSONArray()
                            .put(new JSONObject()
                                    .put("type", "CARD")
                                    .put("parameters", new JSONObject()
                                            .put("allowedAuthMethods",
                                                    new JSONArray().put("PAN_ONLY"))
                                            .put("allowedCardNetworks",
                                                    new JSONArray().put("VISA")))
                                    .put("tokenizationSpecification",
                                            new JSONObject()
                                                    .put("type", "PAYMENT_GATEWAY")
                                                    .put("parameters", new JSONObject()
                                                            .put("gateway","example")))))
                    .put("transactionInfo", new JSONObject()
                            .put("totalPriceStatus","FINAL")
                            .put("totalPrice","1.00")
                            .put("currencyCode","AUD"))
                    .put("merchantInfo", new JSONObject()
                            .put("merchantName","Demo Merchant"));

            PaymentDataRequest payReq =
                    PaymentDataRequest.fromJson(reqJson.toString());

            Task<PaymentData> task = walletClient.loadPaymentData(payReq);
            AutoResolveHelper.resolveTask(task,
                    requireActivity(), LOAD_PAY_REQUEST);

        } catch (Exception e) {
            Toast.makeText(getContext(),
                    "Google Pay is not called", Toast.LENGTH_SHORT).show();
        }
    }


    @Override public void onActivityResult(int c, int r, Intent d) {
        super.onActivityResult(c, r, d);
        if (c == LOAD_PAY_REQUEST) {
            Toast.makeText(getContext(),
                    r == Activity.RESULT_OK ?
                            "closed" : "canceled",
                    Toast.LENGTH_SHORT).show();
        }
    }
}