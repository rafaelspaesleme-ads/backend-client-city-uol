package br.com.compassouol.clientecity.utils.functions;

import org.joda.time.LocalDate;
import org.joda.time.Years;

public class ClienteFunction {
    public static Integer convertDateInAge(java.time.LocalDate dateOfBirth) {
        Years age = Years.yearsBetween(new LocalDate(dateOfBirth.getYear(), dateOfBirth.getMonthValue(), dateOfBirth.getDayOfMonth()), LocalDate.now());
        return age.getYears();
    }

    public static Integer checkAge(Integer ageCurrrent, Integer ageGenerate) {
        if (ageCurrrent.equals(ageGenerate)) {
            return ageCurrrent;
        } else {
            return ageGenerate;
        }
    }
}
