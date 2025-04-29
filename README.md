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