RMA 2025/26 - PROJEKT - SPIRALA 4
Dokumentacija poboljsanja
Student: Imran Custovic, 19950

Spirala 4 zahtijeva poboljsanja iz dvije perspektive:
  - Zadatak 1 (Korisnicka perspektiva): minimalno 2 poboljsanja
  - Zadatak 2 (Razvojna perspektiva):   minimalno 3 poboljsanja
U nastavku su poboljsanja razdvojena po zadacima. Za svako je
navedeno sta je promijenjeno, gdje i zasto.

ZADATAK 1 - KORISNICKA PERSPEKTIVA
Realizovano 5 poboljsanja.

1.1  Indikator ucitavanja (loading spinner)

 Dok aplikacija dohvata kvizove sa backenda, prikazuje se
      kruzni indikator ucitavanja (CircularProgressIndicator).
 Nalazi se u KvizViewModel.kt (novo stanje isLoading),
      KvizoviScreen.kt (prikaz spinnera).
 Prije je ekran bio prazan dok traje mrezni poziv, pa
      korisnik nije znao da li aplikacija radi ili je zaglavila.
      Sada ima jasnu povratnu informaciju da se podaci ucitavaju.

1.2  Prikaz greske uz dugme "Pokusaj ponovo"

  Ako dohvatanje kvizova ne uspije (npr. backend nije
      dostupan), prikazuje se poruka o gresci i dugme za ponovni
      pokusaj.
  Nalazi se u KvizViewModel.kt (stanje greska + try/catch u applyFilter),
      KvizoviScreen.kt (prikaz poruke i dugmeta).
  Prije su se greske "gutale" i korisnik je vidio samo praznu
      listu bez objasnjenja. Sada zna da je doslo do greske i moze
      pokusati ponovo bez restartovanja aplikacije.

1.3  Tekstualni opis statusa kviza uz boju

  Pored statusne ikonice (boje) svakog kviza sada stoji i
      tekst: "Aktivan", "Buduci", "Propusten" ili "Uradjen".
  Nalazi se u KvizItem.kt (prikaz teksta statusa),
      util/KvizStatusHelper.kt (enum KvizStatus sa opisima).
  Prije je status bio prikazan samo bojom, pa je korisnik
      morao pogadjati sta koja boja znaci. Tekst uklanja tu
      nejasnocu i poboljsava pristupacnost.

1.4  Jasno prazno stanje i zakljucavanje odgovora

  (a) Kada nema kvizova za prikaz, ispisuje se poruka
      "Nema kvizova za prikaz." umjesto prazne liste.
      (b) Na ekranu kviza, nakon sto se odgovori na pitanje,
      opcije se zakljucavaju i prikazuje se "Odgovor zabiljezen".
  Nalazi se u KvizoviScreen.kt (prazno stanje),
      KvizDetaljiScreen.kt (zakljucavanje odgovora).
  (a) Prazna lista bez teksta djeluje kao greska; poruka
      jasno govori da jednostavno nema rezultata.
      (b) Prije se odgovor mogao slati vise puta na isto pitanje;
      sada je jasno koje je pitanje vec odgovoreno.

1.5  Samo aktivni kvizovi se mogu raditi

   Kviz se moze otvoriti i zapoceti samo ako je AKTIVAN. Za
      buduce, prosle i vec uradjene kvizove prikazuje se jasna
      poruka umjesto dugmeta "Zapocni kviz":
        - buduci:  "Ovaj kviz jos nije poceo..."
        - prosli:  "Ovaj kviz je istekao i vise se ne moze raditi."
        - uradjen: "Ovaj kviz ste vec uradili."
   Nalazi se u KvizDetaljiScreen.kt (provjera statusa preko odrediStatus).
   Prije se mogao zapoceti bilo koji kviz, ukljucujuci one
      koji su istekli ili jos nisu poceli, sto je logicki
      neispravno. Sada aplikacija dozvoljava rad samo na aktivnom
      kvizu, sto odgovara stvarnom ponasanju sistema kvizova.

ZADATAK 2 - RAZVOJNA PERSPEKTIVA
Realizovano 4 poboljsanja.

2.1  Ispravljeno referentno vrijeme za status kviza

  Status kviza (aktivan/buduci/proslji) racuna se u odnosu na
      stvarno trenutno vrijeme (LocalDateTime.now()).
  Nalazi se u util/KvizStatusHelper.kt, KvizViewModel.kt.
  Prije su postojala DVA razlicita "trenutka": KvizItem je
      koristio fiksni hardkodirani datum, a KvizViewModel
      LocalDateTime.now(). Zbog toga su se status u listi i
      rezultat filtera mogli razlikovati za isti kviz. Sada
      postoji jedna, ispravna logika - status uvijek odrazava
      stvarno stanje u trenutku koristenja.

2.2  Izdvojena logika statusa kviza u zaseban fajl

  Logika koja odredjuje status kviza i pripadajucu boju
      izdvojena je iz UI komponente u poseban fajl
      KvizStatusHelper.kt (enum KvizStatus + funkcije
      odrediStatus i prikazniDatum).
  Nalazi se u util/KvizStatusHelper.kt (nova logika),
      KvizItem.kt i KvizDetaljiScreen.kt (sada koriste helper).
  Prije je ta logika bila zakopana kao privatne funkcije
      unutar KvizItem komponente, pa se nije mogla ponovo
      iskoristiti ni testirati. Izdvajanjem je kod citljiviji,
      logika je na jednom mjestu i koristi je vise ekrana
      (lista kvizova i detalji kviza).


2.3  Centralizacija UI konstanti

  Format datuma (i druge UI konstante) premjesten je u jedan
      objekat Config.
  Nalazi se u config/Config.kt (novi fajl).
  Prije su konstante (npr. format datuma) bile rasute po
      komponentama. Centralizacijom postoji jedan izvor istine,
      pa se izmjena radi na jednom mjestu umjesto na vise.

2.4  Uklanjanje mrtvog (neiskoristenog) koda

  Obrisani su fajlovi i paketi koji se vise ne koriste:
      - data/StaticData.kt (stari staticki podaci)
      - model/Models.kt (stari modeli; koristi se novi paket)
      - util/KvizStatusUtil.kt (zamijenjen KvizStatusHelper-om)
      - stari MainActivity.kt u paketu ba.etfrma.rma26p19950
        (aktivni MainActivity je u paketu ba.etf.rma26.projekat)
      - prazni paketi data/ i model/
  Promjena na paketu ba.etfrma.rma26p19950 (vise fajlova/paketa uklonjeno).
  Prije je projekat sadrzavao dvije razlicite "Kviz" klase i
      neiskoristene staticke podatke, sto zbunjuje novog
      developera. Uklanjanjem mrtvog koda projekat je cisci i
      lakse se snalazi u njemu.

NAPOMENA O STRUKTURI PAKETA, koja je svjesna odluka

Projekat namjerno zadrzava dva paketa:

  - ba.etf.rma26.projekat   -> DATA sloj
       (modeli, repozitoriji, mrezni sloj / ApiConfig, MainActivity)

  - ba.etfrma.rma26p19950   -> PREZENTACIJSKI (UI) sloj
       (ekrani, komponente, ViewModeli, navigacija, util, config)

Ova podjela odgovara razdvajanju data sloja od prezentacijskog
sloja. Paketi NISU spajani jer bi spajanje bilo rizican
refaktoring bez stvarne koristi - aplikacija radi identicno, a
razdvojeni slojevi su pregledniji. Iz tog razloga je struktura
zadrzana svjesno, a ne previdom.

