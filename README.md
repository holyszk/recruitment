recruitment
===========
Aby uruchomić projekt należy:
1) zaimportować oba projekty do środowiska (dla Eclipse - Import -> Existing Android Code Into Workspace) 
2) wygenerować własny klucz dla Google Maps pod adresem: https://cloud.google.com/console dla pakietu: net.redexperts.recruitment ((https://cloud.google.com/console -> APIs & auth -> Credentials)
3) zezwolić na dostęp do 'Google Maps Android API v2' (https://cloud.google.com/console -> APIs & auth -> APIs)
3) w pliku AndroidManifest.xml zmienić klucz do Google Maps na wygenerowany w linii:
        <meta-data 
            android:name="com.google.android.maps.v2.API_KEY"
            android:value="AIzaSyChZ9bq48jlt8u-uPUJdP0-cqK0j_aAhlU" />