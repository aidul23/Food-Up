package com.duodevloopers.foodup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.duodevloopers.foodup.Model.ServiceOrder;
import com.duodevloopers.foodup.callbacks.PrintBottomSheetInteractionCallback;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class PrintServiceBottomSheet implements View.OnClickListener {

    private final BottomSheetDialog bottomSheetDialog;
    private final View bottomSheetView;
    private final PrintBottomSheetInteractionCallback callback;
    private Context context;


    private TextInputEditText pageAmount, documentLink;
    private Spinner pageTypeSpinner, shopSpinner;
    private MaterialButton confirmButton;

    private String[] shops;
    private String[] print;
    private ServiceOrder order;

    public PrintServiceBottomSheet(Context context, PrintBottomSheetInteractionCallback callback) {
        this.callback = callback;
        this.context = context;
        this.shops = context.getResources().getStringArray(R.array.shopName);
        this.print = context.getResources().getStringArray(R.array.printType);

        bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialogTheme);
        bottomSheetView = LayoutInflater.from(context).inflate(R.layout.print_bottom_sheet, null);
    }

    public void showBottomSheet() {

        order = new ServiceOrder("", false, "", "", 0.00, false, String.valueOf(System.currentTimeMillis()), "");

        pageTypeSpinner = bottomSheetView.findViewById(R.id.page_type_spinner);
        shopSpinner = bottomSheetView.findViewById(R.id.shop_spinner);
        pageAmount = bottomSheetView.findViewById(R.id.page_amount);
        documentLink = bottomSheetView.findViewById(R.id.document_link);
        confirmButton = bottomSheetView.findViewById(R.id.confirm_button);

        //shop spinner
        ArrayAdapter<CharSequence> adapterShop = ArrayAdapter.createFromResource(context,
                R.array.shopName, android.R.layout.simple_spinner_item);
        adapterShop.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shopSpinner.setAdapter(adapterShop);
        shopSpinner.setOnItemSelectedListener(new shopSelectedSpinnerClass());

        //print type spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.printType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pageTypeSpinner.setAdapter(adapter);
        pageTypeSpinner.setOnItemSelectedListener(new PrintTypeSpinnerClass());


        bottomSheetDialog.setContentView(bottomSheetView);

        bottomSheetDialog.show();

        confirmButton.setOnClickListener(this);
    }

    public void hideBottomSheet() {
        bottomSheetDialog.dismiss();
    }

    @Override
    public void onClick(View v) {

        if (documentLink.getText().toString().equals("")) {
            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        order.setLink(documentLink.getText().toString().trim());
        order.setPage(Double.parseDouble(pageAmount.getText().toString()));
        placePrintOrder(order);
    }

    private void placePrintOrder(ServiceOrder order) {
        callback.onConfirm();
        hideBottomSheet();
    }

    class PrintTypeSpinnerClass implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            order.setType(print[position]);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    class shopSelectedSpinnerClass implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            order.setType(shops[position]);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }


}
