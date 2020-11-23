package pro.kidsgaurd;

import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class Register extends Fragment {

    EditText etPhoneRegister , etPassRegister , etEmailRegister;
    TextInputLayout inputLayoutPhone, inputLayoutPassword, inputLayoutEmail;
    Button btnRegister;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(pro.kidsgaurd.R.layout.actfrag_register, container, false);
        findView(view);

        return view;
    }

    //findView method for input and populate all elements
    public void findView(View view){

        etPhoneRegister = (EditText)view.findViewById(pro.kidsgaurd.R.id.edtPhoneRegister);
        //etEmailRegister = (EditText)view.findViewById(pro.kidsgaurd.R.id.edtEmailRegister);
        etPassRegister = (EditText)view.findViewById(pro.kidsgaurd.R.id.edtPassRegister);
        btnRegister = (Button)view.findViewById(pro.kidsgaurd.R.id.btnRegister);

        inputLayoutPhone = (TextInputLayout)view.findViewById(pro.kidsgaurd.R.id.inputTextPhoneReg);
        inputLayoutPassword = (TextInputLayout)view.findViewById(pro.kidsgaurd.R.id.inputTextPassReg);
        //inputLayoutEmail = (TextInputLayout)view.findViewById(pro.kidsgaurd.R.id.inputTextEmailReg);
    btnRegister.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            submitForm();
        }
    });

}


    private void submitForm() {
        if (!validatePhone()){
            return;
        }

        if (!validatePassword()){
            return;
        }

        RegisterClass.regis(getContext(),etPhoneRegister.getText().toString(),etPassRegister.getText().toString(),"");


    }


    private boolean validatePhone(){
        String phone = etPhoneRegister.getText().toString().trim();
        if (phone.isEmpty()){
            inputLayoutPhone.setError(getString(pro.kidsgaurd.R.string.err_phone));
            etPhoneRegister.requestFocus();
            return false;
        }else {
            inputLayoutPhone.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateEmail(){
        String email = etEmailRegister.getText().toString().trim();
        if (email.isEmpty()||!isvalidateEmail(email)){
            inputLayoutEmail.setError(getString(pro.kidsgaurd.R.string.err_email));
            etEmailRegister.requestFocus();
            return false;
        }else {
            inputLayoutEmail.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePassword(){
        if (etPassRegister.getText().toString().trim().isEmpty()){
            inputLayoutPassword.setError(getString(pro.kidsgaurd.R.string.err_password));
            etPassRegister.requestFocus();
            return false;
        }else {
            inputLayoutPassword.setErrorEnabled(false);
        }
        return true;
    }

    private static boolean isvalidatePhone(String phone){
        return !TextUtils.isEmpty(phone)&& Patterns.PHONE.matcher(phone).matches();
    }

    private static boolean isvalidateEmail(String email){
        return !TextUtils.isEmpty(email)&& Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}

