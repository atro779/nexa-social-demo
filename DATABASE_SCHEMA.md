# Local data schema
This MVP uses Android SharedPreferences (`nexa`) rather than SQLite.
- `name`: local nickname
- `country`: local country
- `bio`: local biography
- `ready`: onboarding completion
- `chat_<profile>`: local transcript for each fictional demo profile

Wallet balance is a display-only constant. No financial transactions are recorded.
