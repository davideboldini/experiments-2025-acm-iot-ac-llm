## Configurazione delle Chiavi API

Per utilizzare i servizi esterni (GEMINI, OPENROUTER, GITHUB) con questa applicazione, è necessario fornire le relative chiavi API o token. Sono disponibili due metodi per configurare queste credenziali:

### Opzione 1: Utilizzo del File `ApiKeys.txt`

Puoi inserire le tue chiavi API in un file di testo dedicato. Segui questi passaggi:

1.  All'interno della cartella `keys/`, crea un file di testo chiamato `ApiKeys.txt`.
2.  Modifica il file `ApiKeys.txt` e inserisci le chiavi nel seguente formato:

    ```txt
    GEMINI_API_KEY: la_tua_chiave_gemini
    OPENROUTER_API_KEY: la_tua_chiave_openrouter
    GITHUB_TOKEN: il_tuo_token_github
    ```

    **Nota Importante:** Non è obbligatorio inserire tutte e tre le chiavi. Includi nel file solo le righe relative ai servizi che intendi effettivamente utilizzare.

### Opzione 2: Utilizzo di Variabili d'Ambiente

In alternativa, puoi configurare le chiavi API come variabili d'ambiente a livello del tuo sistema operativo. L'applicazione cercherà le chiavi con gli stessi nomi specificati per il file `ApiKeys.txt`.

Assicurati che le seguenti variabili d'ambiente siano impostate nel tuo sistema:

* `GEMINI_API_KEY`
* `OPENROUTER_API_KEY`
* `GITHUB_TOKEN`

La procedura per impostare variabili d'ambiente varia in base al sistema operativo (es. usando `export` su Linux/macOS o tramite le impostazioni di sistema su Windows). Anche in questo caso, imposta solo le variabili per i servizi che desideri utilizzare.

---

L'applicazione cercherà prima le variabili d'ambiente e, se non trovate, leggerà il file `keys/ApiKeys.txt`. Fornire le chiavi tramite uno di questi metodi consentirà all'applicazione di accedere ai rispettivi servizi esterni.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

## API Key Configuration

To use external services (GEMINI, OPENROUTER, GITHUB) with this application, you need to provide the corresponding API keys or tokens. Two methods are available to configure these credentials:

### Option 1: Using the `ApiKeys.txt` File

You can place your API keys in a dedicated text file. Follow these steps:

1.  Inside the `keys/` folder, create a text file named `ApiKeys.txt`.
2.  Edit the `ApiKeys.txt` file and insert your keys in the following format:

    ```txt
    GEMINI_API_KEY: your_gemini_key
    OPENROUTER_API_KEY: your_openrouter_key
    GITHUB_TOKEN: your_github_token
    ```

    **Important Note:** It is not mandatory to include all three keys. Only include the lines for the services you actually intend to use.

### Option 2: Using Environment Variables

Alternatively, you can configure your API keys as environment variables at your operating system level. The application will look for keys with the same names specified for the `ApiKeys.txt` file.

Ensure the following environment variables are set in your system:

* `GEMINI_API_KEY`
* `OPENROUTER_API_KEY`
* `GITHUB_TOKEN`

The procedure for setting environment variables varies depending on your operating system (e.g., using `export` on Linux/macOS or via system settings on Windows). Again, only set the variables for the services you wish to use.

---

The application will first check for environment variables and, if not found, will read the `keys/ApiKeys.txt` file. Providing keys through either of these methods will allow the application to access the respective external services.