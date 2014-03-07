package logic;


import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author khoffmann
 */
public class Nagranie {

    private Path sciezka;
    private String stacja;
    private String ID;
    private Date data;
    private long rozmiar;

    /**
     * Zwraca nazwê stacji na podstawie nazwy pliku
     *
     * @param nazwaNagrania
     * @return
     */
    
    public Nagranie (Path path){
        this.sciezka = path;
        
    }
    
    protected static String getStacja(String nazwaNagrania) {

        String stacja;
        Pattern pattern = Pattern.compile("](.*?)_20");
        Matcher matcher = pattern.matcher(nazwaNagrania);
        if (matcher.matches()) {
            stacja = matcher.group(1);
        } else {
            stacja = "REGEX_ERROR_!!";
        }

        return stacja;
    }

    /**
     * Zwraca ID nagrania na podstawie nazwy pliku Nie nale¿y podawaæ ca³ej
     * œcie¿ki - w takiej sytuacji jako argument podajemy:
     * path.getFileName().toString();
     *
     * @param nazwaNagrania
     * @return
     */
    protected static String getID(String nazwaNagrania) {
        String ID;
        Pattern pattern = Pattern.compile("\\d{7}");
        Matcher matcher = pattern.matcher(nazwaNagrania);
        if (matcher.matches()) {
            ID = matcher.group();
        } else {
            ID = "no_sync";
        }
        return ID;
    }

    /**
     * Zwraca datê i godzinê emisji nagrania na podstawie nazwy pliku
     *
     * @param nazwaNagrania
     * @return Date oznaczaj¹cy pocz¹tek nagrania
     */
    protected static String getDataString(String nazwaNagrania) {
        String date;

        Pattern pattern = Pattern.compile("\\d{4}\\-\\d{2}\\-\\d{2}_godz_\\d{2}_\\d{2}_\\d{2}");
        Matcher matcher = pattern.matcher(nazwaNagrania);
        if (matcher.find()) {
            date = matcher.group();
        } else {
            date = "0000-00-00_godz_00_00_00";
        }

        return date;
    }

    protected static Date getData(String DataString) {
        Date data = null;

        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd_'godz'_HH_mm_ss");

        try {
            data = format.parse(DataString);
        } catch (ParseException e) {
            System.out.println(e);
        }
        return data;
    }

    protected static long getRozmiar(Path path) {

        long Rozmiar;
        Rozmiar = path.toFile().length();
        return Rozmiar;

    }
    public Path getPath(){
        return sciezka;
    }
    
    public String getStacja(){
        return stacja;
    }
    public String getID(){
        return ID;
    }
    public Date getData(){
        return data;
    }
    public long getRozmiar(){
        return rozmiar;
    }
}
