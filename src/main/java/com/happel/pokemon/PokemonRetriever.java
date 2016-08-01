package com.happel.pokemon;

import com.pokegoapi.api.PokemonGo;
import com.pokegoapi.api.inventory.Inventories;
import com.pokegoapi.api.pokemon.Pokemon;
import com.pokegoapi.auth.CredentialProvider;
import com.pokegoapi.auth.GoogleAutoCredentialProvider;
import com.pokegoapi.auth.PtcCredentialProvider;
import com.pokegoapi.exceptions.LoginFailedException;
import com.pokegoapi.exceptions.RemoteServerException;
import okhttp3.*;

import java.util.Arrays;
import java.util.List;


public class PokemonRetriever {

    public static List<Pokemon> getAllPokemon(Credentials credentials) {
        if (credentials == null) {
            return Arrays.asList(new Pokemon[]{});
        }
        PokemonGo go = getGoForCredentials(credentials);
        // to get all his inventories (pokemon, backpack, egg, incubator)
        final Inventories inventories = go.getInventories();
        List<Pokemon> pokemon = inventories.getPokebank().getPokemons();
        return pokemon;
    }

    private static PokemonGo getGoForCredentials(Credentials credentials) {
        final OkHttpClient httpClient = new OkHttpClient();
        final String username = credentials.username;
        final String password = credentials.password;
        PokemonGo go = null;
        try {
            CredentialProvider provider = null;
            switch (credentials.credentialType) {
                case GOOGLE:
                    provider = new GoogleAutoCredentialProvider(httpClient, username, password);
                    break;
                case PTC:
                    provider = new PtcCredentialProvider(httpClient, username, password);
                    break;
            }
            go = new PokemonGo(provider, httpClient);
        } catch (LoginFailedException e) {
            e.printStackTrace();
        } catch (RemoteServerException e) {
            e.printStackTrace();
        }
        return go;
    }
}
