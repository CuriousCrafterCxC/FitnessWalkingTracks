package hk.edu.hkmu.fitness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Set;

public class SettingsActivity extends AppCompatActivity {
    public TextView text_language_selection;
    public Button confirm_button;
    public Button cancel_button;

    RadioGroup langSelections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        text_language_selection = findViewById(R.id.txt_setting_lang);
        confirm_button = findViewById(R.id.saveSettings);
        cancel_button = findViewById(R.id.noSave);
        String lang = Settings.getLang();
        switch (lang){
            case "en":
                text_language_selection.setText("Switch Language:");
                confirm_button.setText("CONFIRM");
                cancel_button.setText("CANCEL");
                break;
            case "sc":
                text_language_selection.setText("切换语言:");
                confirm_button.setText("确认");
                cancel_button.setText("取消");
                break;
            case "tc":
                text_language_selection.setText("轉換語言:");
                confirm_button.setText("確認");
                cancel_button.setText("取消");
                break;
        }
        langSelections = findViewById(R.id.lang_group);
        langSelections.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
               switch (checkedId){
                   case R.id.lang_en:
                       Settings.setSelection("en");
                       break;
                   case R.id.lang_sc:
                       Settings.setSelection("sc");
                       break;
                   case R.id.lang_tc:
                       Settings.setSelection("tc");
                       break;
               }
            }
        });
        confirm_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Settings.setLang(Settings.getSelection());
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        cancel_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
    }
}
