package com.moutamid.torchapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.PurchaseInfo;

public class DonateActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler {

    //    BillingClient billingClient;

    BillingProcessor bp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        bp = BillingProcessor.newBillingProcessor(this, Constants.LICENSE_KEY, this);
        bp.initialize();

        /*findViewById(R.id.twohundred).setOnClickListener((View.OnClickListener) view -> {
            bp.purchase(DonateActivity.this, Constants.TWO_HUNDRED_DOLLAR_PRODUCT);
        });

        findViewById(R.id.twotwofive).setOnClickListener((View.OnClickListener) view -> {
            bp.purchase(DonateActivity.this, Constants.TWO_TWENTY_FIVE_DOLLAR_PRODUCT);
        });

        findViewById(R.id.twofoursix).setOnClickListener((View.OnClickListener) view ->
                bp.purchase(DonateActivity.this, Constants.TWO_FORTY_SIX_DOLLAR_PRODUCT)
        );
        findViewById(R.id.twosixfive).setOnClickListener((View.OnClickListener) view ->
                bp.purchase(DonateActivity.this, Constants.TWO_SIXTY_FIVE_DOLLAR_PRODUCT)
        );
        findViewById(R.id.threehundred).setOnClickListener((View.OnClickListener) view ->
                bp.purchase(DonateActivity.this, Constants.THREE_HUNDRED_DOLLAR_PRODUCT)
        );
*/
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable PurchaseInfo details) {
        Toast.makeText(DonateActivity.this, "Purchase successful", Toast.LENGTH_SHORT).show();
        TextView textView = findViewById(R.id.purchasedTextView);

        if (productId.equals( Constants.TWO_HUNDRED_DOLLAR_PRODUCT))
            textView.setText("Two hundred tokens purchased");
        if (productId.equals( Constants.TWO_TWENTY_FIVE_DOLLAR_PRODUCT))
            textView.setText("Two twenty five tokens purchased");
        if (productId.equals( Constants.TWO_FORTY_SIX_DOLLAR_PRODUCT))
            textView.setText("Two forty six tokens purchased");
        if (productId.equals( Constants.TWO_SIXTY_FIVE_DOLLAR_PRODUCT))
            textView.setText("Two sixty five tokens purchased");
        if (productId.equals( Constants.THREE_HUNDRED_DOLLAR_PRODUCT))
            textView.setText("Three hundred purchased");

        textView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {
        Toast.makeText(DonateActivity.this, "onBillingError: code: " + errorCode + " \n" + error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBillingInitialized() {

    }

    @Override
    protected void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }
}