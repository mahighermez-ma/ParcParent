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


public class Login extends Fragment {

    EditText etPhoneLogin , etPassLogin ;
    TextInputLayout inputLayoutPhone, inputLayoutPassword;
    Button btnLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(pro.kidsgaurd.R.layout.actfrag_login, container, false);
        findView(view);

        return view;
    }

    //findView method for input and populate all elements
    public void findView(View view){

    btnLogin = (Button) view.findViewById(pro.kidsgaurd.R.id.btnLogin);
    etPhoneLogin = (EditText)view.findViewById(pro.kidsgaurd.R.id.edtPhoneLogin);
    etPassLogin = (EditText)view.findViewById(pro.kidsgaurd.R.id.edtPassLogin);

    inputLayoutPhone = (TextInputLayout)view.findViewById(pro.kidsgaurd.R.id.inputTextPhoneLogin);
    inputLayoutPassword = (TextInputLayout)view.findViewById(pro.kidsgaurd.R.id.inputTextPassLogin);



    btnLogin.setOnClickListener(new View.OnClickListener() {
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

        loginClass.log(getContext(),etPhoneLogin.getText().toString(),etPassLogin.getText().toString());

    }


    private boolean validatePhone(){
        String phone = etPhoneLogin.getText().toString().trim();
if (phone.isEmpty()){
    inputLayoutPhone.setError(getString(pro.kidsgaurd.R.string.err_phone));
    etPhoneLogin.requestFocus();
    return false;
}else {
    inputLayoutPhone.setErrorEnabled(false);
}
return true;
    }

    private boolean validatePassword(){
        if (etPassLogin.getText().toString().trim().isEmpty()){
            inputLayoutPassword.setError(getString(pro.kidsgaurd.R.string.err_password));
            etPassLogin.requestFocus();
            return false;
        }else {
            inputLayoutPassword.setErrorEnabled(false);
        }
        return true;
    }

    private static boolean isvalidatePhone(String phone){
        return !TextUtils.isEmpty(phone)&& Patterns.PHONE.matcher(phone).matches();
    }
}
