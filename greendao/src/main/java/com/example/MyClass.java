package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class MyClass extends Object {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "felix.lightmusic");

        //addNote(schema);
        addStaticData(schema);
        new DaoGenerator().generateAll(schema, "D:\\FelixProgramFiles\\developer\\AndroidProject\\AndroidStudio\\Demo\\LightMusic\\app\\src\\main\\java\\greendao");

    }

    private static void addStaticData(Schema schema) {
        Entity music = schema.addEntity("Music");
        Property musicId = music.addLongProperty("id").primaryKey().getProperty();
        music.addStringProperty("title").index();
        music.addStringProperty("name").index();
        music.addStringProperty("artist").index();
        music.addStringProperty("album");
        music.addLongProperty("duration");
        music.addLongProperty("size");
        music.addStringProperty("url");
        music.addBooleanProperty("isFavorite").notNull();

        Entity musicList = schema.addEntity("MusicList");
        Property musicListId = musicList.addLongProperty("id").primaryKey().getProperty();
        musicList.addStringProperty("name").notNull().unique();

        Entity musicListItem = schema.addEntity("MusicListItem");
        musicListItem.addLongProperty("id").primaryKey().getProperty();
        Property musicfk = musicListItem.addLongProperty("musicId").unique().getProperty();
        Property musicListfk = musicListItem.addLongProperty("musicListId").unique().getProperty();

        musicListItem.addToOne(music, musicfk);
        music.addToMany(musicListItem, musicfk).setName("musicsItems");


        musicListItem.addToOne(musicList, musicListfk);
        musicList.addToMany(musicListItem, musicListfk).setName("musicsItems");

    }

}
