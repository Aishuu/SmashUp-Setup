package fr.eurecom.smashupsetup;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.LinearLayout;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.Drawable;
import android.content.res.Resources;
import android.util.Log;
import android.graphics.Color;


public class MainActivity extends Activity
{
    public static final String ALREADY_DRAWN = "alreadyDrawn";
    public static final int MIN_PLAYER = 2;
    public static final int MAX_PLAYER = 4;

    private int nb_players = 2;
    private boolean[] extensions = {true, true, false, true, false, false};

    private int[] nb_races =       {8,    4,    4,     4,    4,     4};
    private String[] races = {
            "Robots",
            "Aliens",
            "Pirates",
            "Zombies",
            "Mages",
            "Ninjas",
            "Dinosaures",
            "Petits Etres",
            "Steampunks",
            "Plantes Carnivores",
            "Cavalerie Ours",
            "Fantomes",
            "Cultistes",
            "Habitants d'Innsmouth",
            "Choses Anciennes",
            "Universitaires de Miskatonic",
            "Espions",
            "Voyageurs dans le Temps",
            "Changeurs de Forme",
            "Singes",
            "Loups Garou",
            "Vampires",
            "Fourmis",
            "Scientifiques",
            "Princesses",
            "Chatons",
            "Fees",
            "Licornes"
    };
    private int[][] imagesres = {
            {R.drawable.robots_1, R.drawable.robots_2},
            {R.drawable.envahisseurs_1, R.drawable.envahisseurs_2},
            {R.drawable.pirates_1, R.drawable.pirates_2},
            {R.drawable.zombies_1, R.drawable.zombies_2},
            {R.drawable.mages_1, R.drawable.mages_2},
            {R.drawable.ninjas_1, R.drawable.ninjas_2},
            {R.drawable.dinos_1, R.drawable.dinos_2},
            {R.drawable.tricksters_1, R.drawable.tricksters_2},
            {R.drawable.steampunks_1, R.drawable.steampunks_2},
            {R.drawable.plantes_1, R.drawable.plantes_2},
            {R.drawable.ours_1, R.drawable.ours_2},
            {R.drawable.fantomes_1, R.drawable.fantomes_2},
            {R.drawable.cultistes_1, R.drawable.cultistes_2},
            {R.drawable.innsmouth_1, R.drawable.innsmouth_2},
            {R.drawable.choses_1, R.drawable.choses_2},
            {R.drawable.universitaires_1, R.drawable.universitaires_2},
            {R.drawable.spy_1, R.drawable.spy_2},
            {R.drawable.voyageurs_1, R.drawable.voyageurs_2},
            {R.drawable.changeurs_1, R.drawable.changeurs_2},
            {R.drawable.singes_1, R.drawable.singes_2},
            {R.drawable.loups_1, R.drawable.loups_2},
            {R.drawable.vampires_1, R.drawable.vampires_2},
            {R.drawable.ants_1, R.drawable.ants_2},
            {R.drawable.scientifiques_1, R.drawable.scientifiques_2},
            {R.drawable.princesses_1, R.drawable.princesses_2},
            {R.drawable.chats_1, R.drawable.chats_2},
            {R.drawable.fees_1, R.drawable.fees_2},
            {R.drawable.licornes_1, R.drawable.licornes_2}
    };
    private String[] player_names = {
            "Adrien",
            "Florian",
            "Mathieu",
            "Michel"
    };
    private boolean[] already_drawn;
    private int nb_already_drawn = 0;

    private MenuItem addPlayer, removePlayer;
    private Menu extSubMenu;
    private TextView[] textPlayer;
    private ImageView[] contentPlayer;
    private Button nextButton;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            this.already_drawn = savedInstanceState.getBooleanArray(ALREADY_DRAWN);
            for (int i=0; i<this.already_drawn.length; i++)
                if (this.already_drawn[i])
                    this.nb_already_drawn ++;
        } else
            this.already_drawn = new boolean[this.races.length];
        setContentView(R.layout.main);
        LinearLayout global_layout = (LinearLayout) findViewById(R.id.global_layout);
        this.textPlayer = new TextView[4];
        this.contentPlayer = new ImageView[4];
        LinearLayout[] layout = {
            (LinearLayout) global_layout.getChildAt(0),
            (LinearLayout) global_layout.getChildAt(1)
        };
        for (int i=0; i<4; i++) {
            LinearLayout ll = (LinearLayout) layout[i/2].getChildAt(i%2);
            this.textPlayer[i] = (TextView) ll.getChildAt(0);
            this.contentPlayer[i] = (ImageView) ll.getChildAt(1);
        }
        this.nextButton = (Button) findViewById(R.id.nextButton);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBooleanArray(ALREADY_DRAWN, this.already_drawn);

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater inflater = getMenuInflater ();
        inflater.inflate (R.menu.main, menu);
        this.addPlayer = menu.getItem(0).getSubMenu().getItem(0);
        this.removePlayer = menu.getItem(0).getSubMenu().getItem(1);
        this.extSubMenu = menu.getItem(1).getSubMenu();
        this.updateMenu();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int i =0;
        switch (item.getItemId()) {
            case R.id.addPlayer:
                if (this.nb_players < 4)
                    this.nb_players ++;
                this.updateMenu();
                return true;
            case R.id.removePlayer:
                if (this.nb_players > 2)
                    this.nb_players --;
                this.updateMenu();
                return true;
            case R.id.extPrettyPretty:
                i++;
            case R.id.extMonsterSmash:
                i++;
            case R.id.extSeriesB:
                i++;
            case R.id.extCthulhuFhtagn:
                i++;
            case R.id.extMemePasMort:
                i++;
            case R.id.extBase:
                this.extensions[i] = !this.extensions[i];
                this.updateMenu();
                return true;
            default:
                return super.onOptionsItemSelected (item);
        }
    }

    private void updateView () {
        int nb_races = 0;
        for (int i=0; i<this.extensions.length; i++)
            if (this.extensions[i])
                nb_races += this.nb_races[i];
        if (this.nb_already_drawn + 2*this.nb_players > nb_races || this.nb_already_drawn == 0)
            this.nextButton.setVisibility(View.INVISIBLE);
        else
            this.nextButton.setVisibility(View.VISIBLE);
    }

    private void resetView () {
        for (int i=0; i<4; i++) {
            this.textPlayer[i].setText("");
            this.textPlayer[i].setTextColor(Color.WHITE);
            this.contentPlayer[i].setVisibility(View.INVISIBLE);
        }
        this.updateView();
    }

    private void updateMenu () {
        this.addPlayer.setEnabled(this.nb_players < 4);
        this.removePlayer.setEnabled(this.nb_players > 2);
        for (int i=0; i<this.extensions.length; i++)
            this.extSubMenu.getItem(i).setChecked(this.extensions[i]);
        this.resetView();
    }

    public void drawButton (View v) {
        this.already_drawn = new boolean[this.races.length];
        this.nb_already_drawn = 0;
        draw ();
    }

    public void drawNextButton (View v) {
        draw ();
    }

    private void draw () {
        int nb_races = 0;
        for (int i=0; i<this.extensions.length; i++)
            if (this.extensions[i])
                nb_races += this.nb_races[i];
        if (nb_races == 0)
            return;

        this.resetView();

        for (int i=0; i<this.nb_players; i++) {
            this.textPlayer[i].setText(this.player_names[i]);
            int races[] = new int[2];
            for (int j=0; j<2; j++) {
                int n = (int) (Math.random() * (nb_races - this.nb_already_drawn));
                int cumul = 0;
                search:
                for (int k=0; k<this.extensions.length; k++)
                    if (this.extensions[k]) {
                        for (int l=0; l<this.nb_races[k]; l++) {
                            if (!this.already_drawn[cumul]) {
                                if (n == 0)
                                    break search;
                                n --;
                            }
                            cumul ++;
                        }
                    } else
                        cumul += this.nb_races[k];
                this.already_drawn[cumul] = true;
                this.nb_already_drawn ++;
                races[j] = cumul;
            }

            Resources r = getResources();
            Drawable[] layers = new Drawable[2];
            layers[0] = r.getDrawable(this.imagesres[races[0]][1]);
            layers[1] = r.getDrawable(this.imagesres[races[1]][0]);
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            this.contentPlayer[i].setImageDrawable(layerDrawable);
            this.contentPlayer[i].setVisibility(View.VISIBLE);
        }

        int turn = (int) (Math.random() * this.nb_players);
        this.textPlayer[turn].setTextColor(Color.RED);

        this.updateView();
    }
}
