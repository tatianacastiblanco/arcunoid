package com.cun.arcunoid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.utils.Align;

/**
 * Created by CUN on 1/02/2018.
 */

public class OnlineRegister extends BaseScreen{
    Image signText, nameBox, lastnameBox, mailBox, userBox, passBox, phoneBox;
    TextField name, lastname, mail, user, pass, phone;
    ImageButton signUp, menu;
    private Label onlineMessage;

    private boolean onOut = false;

    OnlineRegister(arCunoid g) {
        super(g);

        TextureAtlas menuAtlas = new TextureAtlas("botones.txt");
        TextureAtlas signAtlas = new TextureAtlas("menu.txt");

        signText = new Image(new Texture("signText.png"));
        signText.setPosition(127.534f, 1562.315f);

        Label.LabelStyle labelStyle = new Label.LabelStyle(game.fontSmall, Color.YELLOW);
        onlineMessage = new Label("", labelStyle);
        onlineMessage.setWrap(true);
        onlineMessage.setBounds(0, 1270, 1080, 400);
        onlineMessage.setAlignment(Align.center);

        TextField.TextFieldStyle fieldStyle = new TextField.TextFieldStyle();
        fieldStyle.font = game.font;
        fieldStyle.fontColor = Color.WHITE;
        nameBox = new Image(new Texture("name.png"));
        nameBox.setPosition(134.534f, 934);
        name = new TextField("Nombre", fieldStyle);
        name.setBounds(314.534f, 934, 640, 122);
        name.setMaxLength(15);
        name.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(name.getText().equals("Nombre"))
                    name.setText("");
                return  true;
            }
        });
        lastnameBox = new Image(new Texture("name.png"));
        lastnameBox.setPosition(134.534f, 762.75f);
        lastname = new TextField("Apellido", fieldStyle);
        lastname.setBounds(314.534f, 762.75f, 640, 122);
        lastname.setMaxLength(15);
        lastname.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(lastname.getText().equals("Apellido"))
                    lastname.setText("");
                return  true;
            }
        });
        mailBox = new Image(new Texture("mail.png"));
        mailBox.setPosition(134.534f, 592);
        mail = new TextField("Correo", fieldStyle);
        mail.setBounds(314.534f, 592, 640, 122);
        mail.setMaxLength(20);
        mail.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(mail.getText().equals("Correo"))
                    mail.setText("");
                return  true;
            }
        });
        phoneBox = new Image(new Texture("name.png"));
        phoneBox.setPosition(134.534f, 422.25f);
        phone = new TextField("Telefono", fieldStyle);
        phone.setBounds(314.534f, 422.25f, 640, 122);
        phone.setMaxLength(10);
        phone.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter.DigitsOnlyFilter());
        phone.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(phone.getText().equals("Telefono"))
                    phone.setText("");
                return  true;
            }
        });
        userBox = new Image(new Texture("userName2.png"));
        userBox.setPosition(134.534f, 1265.5f);
        user = new TextField("Usuario", fieldStyle);
        user.setBounds(314.534f, 1265.5f, 640, 122);
        user.setMaxLength(10);
        user.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(user.getText().equals("Usuario"))
                    user.setText("");
                return  true;
            }
        });
        passBox = new Image(new Texture("passw2.png"));
        passBox.setPosition(134.534f, 1100.75f);
        pass = new TextField("Contraseña", fieldStyle);
        pass.setBounds(314.534f, 1100.75f, 640, 122);
        pass.setMaxLength(15);
        pass.isPasswordMode();
        pass.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(pass.getText().equals("Contraseña"))
                    pass.setText("");
                return  true;
            }
        });

        Skin signSkin = new Skin(signAtlas);
        Skin menuSkin = new Skin(menuAtlas);
        signUp = new ImageButton(signSkin.getDrawable("btnSignUp"), signSkin.getDrawable("btnSignDown"));
        signUp.setPosition(313.139f, 211.321f);
        signUp.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return  true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                String text = user.getText().replace(" ", "");
                user.setText(text);
                text = pass.getText().replace(" ", "");
                pass.setText(text);
                text = name.getText().replace(" ", "");
                name.setText(text);
                text = mail.getText().replace(" ", "");
                mail.setText(text);
                text = lastname.getText().replace(" ", "");
                lastname.setText(text);
                text = phone.getText().replace(" ", "");
                phone.setText(text);
                if(!user.getText().isEmpty() && !pass.getText().isEmpty() && !name.getText().isEmpty() && !mail.getText().isEmpty())
                    game.addUser(user.getText(), pass.getText(), name.getText(), lastname.getText(), mail.getText(), phone.getText());
                else
                    onlineMessage.setText("Los campos usuario, contraseña, nombre\ny correo no pueden estar vacios");
            }
        });
        menu = new ImageButton(menuSkin.getDrawable("btnStartUp"), menuSkin.getDrawable("btnStartDown"));
        menu.setPosition(313.139f, 40);
        menu.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return  true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                onOut = true;
                game.addTransitionOutScreen("Menu");
            }
        });
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0.0745f, 0.13f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        /*if(onOut)
            menuMusic.setVolume(menuMusic.getVolume()-0.05f);
        if(!game.getMusicOn())
            menuMusic.pause();
        else
            menuMusic.play();
        */
        if(game.created != 0){
            if(game.created == 2){
                onOut = true;
                game.addTransitionOutScreen("Story0");
                game.created = 0;
            }else if(game.created == 3){
                Gdx.app.log("Entre", "NOP");
                onlineMessage.setText(game.textOnline);
                game.created = 0;
            }
        }
        camera.update();
        batch.begin();
        drawBack(delta, true);
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show(){
        stage.addActor(signText);
        stage.addActor(onlineMessage);
        stage.addActor(nameBox);
        stage.addActor(name);
        stage.addActor(lastnameBox);
        stage.addActor(lastname);
        stage.addActor(mailBox);
        stage.addActor(mail);
        stage.addActor(userBox);
        stage.addActor(user);
        stage.addActor(passBox);
        stage.addActor(pass);
        stage.addActor(phoneBox);
        stage.addActor(phone);
        stage.addActor(signUp);
        stage.addActor(menu);
        game.addTransitionInScreen();
        music.setPosition(975.581f, 1821.267f);
        stage.addActor(music);
    }

    @Override
    public void hide(){

    }

    @Override
    public void dispose(){

    }
}
