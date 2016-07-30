package com.happel.pokemon;

import com.pokegoapi.api.PokemonGo;
import com.pokegoapi.api.inventory.Inventories;
import com.pokegoapi.api.pokemon.Pokemon;
import com.pokegoapi.auth.GoogleAutoCredentialProvider;
import com.pokegoapi.auth.PtcCredentialProvider;
import com.pokegoapi.exceptions.LoginFailedException;
import com.pokegoapi.exceptions.RemoteServerException;
import okhttp3.*;

import java.util.List;


public class PokemonRetriever {

    public static void main(String[] args) {
        List<Pokemon> pokemon = getAllPokemon();
        for (Pokemon p: pokemon) {
            System.out.println(p.getCandy());
            System.out.println(p.getPokemonId());
            System.out.println(p.getMeta().getFamily());
        }
    }

    public static List<Pokemon> getAllPokemon() {
        PokemonGo go = getGoWithGoogle();
//        PokemonGo go = getGoWithPtc();
        // to get all his inventories (pokemon, backpack, egg, incubator)
        final Inventories inventories = go.getInventories();
        List<Pokemon> pokemon = inventories.getPokebank().getPokemons();
        return pokemon;
    }

    private static PokemonGo getGoWithPtc() {
        final OkHttpClient httpClient = new OkHttpClient();
        final String username = Credentials.PTC.username;
        final String password = Credentials.PTC.password;
        PokemonGo go = null;
        try {
            go = new PokemonGo(new PtcCredentialProvider(httpClient, username, password), httpClient);
        } catch (LoginFailedException e) {
            e.printStackTrace();
        } catch (RemoteServerException e) {
            e.printStackTrace();
        }
        return go;
    }

    private static PokemonGo getGoWithGoogle() {
        final OkHttpClient httpClient = new OkHttpClient();
        Credentials credentials = Credentials.GOOGLE;
        final String username = credentials.username;
        final String password = credentials.password;
        PokemonGo go = null;
        try {
            GoogleAutoCredentialProvider provider = new GoogleAutoCredentialProvider(httpClient, username, password);
            go = new PokemonGo(provider, httpClient);
        } catch (LoginFailedException e) {
            e.printStackTrace();
        } catch (RemoteServerException e) {
            e.printStackTrace();
        }
        return go;
    }
}
