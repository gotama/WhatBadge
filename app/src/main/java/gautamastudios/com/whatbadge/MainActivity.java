package gautamastudios.com.whatbadge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Badge badge;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        badge = findViewById(R.id.badge);
        button = findViewById(R.id.hide_button);
        button.setTag(false);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((boolean) button.getTag()) {
                    button.setTag(false);
                    badge.hideOuterBorder(false);
                    badge.hideCircleBorder(true);
                } else {
                    button.setTag(true);
                    badge.hideOuterBorder(true);
                    badge.hideCircleBorder(false);
                }


            }
        });
    }
}
