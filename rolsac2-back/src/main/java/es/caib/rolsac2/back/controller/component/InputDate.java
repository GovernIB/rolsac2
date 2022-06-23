package es.caib.rolsac2.back.controller.component;


import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIInput;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.event.AjaxBehaviorEvent;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@FacesComponent(createTag = true, tagName = "inputDate", namespace = "http://back.rolsac2.caib.es/tags") //("inputDate")
public class InputDate extends UIInput implements NamingContainer {

    // Fields -------------------------------------------------------------------------------------

    private UIInput day;
    private UIInput month;
    private UIInput year;
 
    // Actions ------------------------------------------------------------------------------------

    /**
     * Returns the component family of {@link UINamingContainer}.
     * (that's just required by composite component)
     */
    @Override
    public String getFamily() {
        return UINamingContainer.COMPONENT_FAMILY;
    }

    /**
     * Set the selected and available values of the day, month and year fields based on the model.
     */
    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        Calendar calendar = Calendar.getInstance();
        int maxYear = getAttributeValue("maxyear", calendar.get(Calendar.YEAR));
        int minYear = getAttributeValue("minyear", maxYear - 100);
        Date date = (Date) getValue();

        if (date != null) {
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);

            if (year > maxYear || minYear > year) {
                throw new IllegalArgumentException(
                        String.format("Year %d out of min/max range %d/%d.", year, minYear, maxYear));
            }
        }

        day.setValue(calendar.get(Calendar.DATE));
        month.setValue(calendar.get(Calendar.MONTH) + 1);
        year.setValue(calendar.get(Calendar.YEAR));
        setDays(createIntegerArray(1, calendar.getActualMaximum(Calendar.DATE)));
        setMonths(createIntegerArray(1, calendar.getActualMaximum(Calendar.MONTH) + 1));
        setYears(createIntegerArray(maxYear, minYear));
        super.encodeBegin(context);
    }

    /**
     * Returns the submitted value in dd-MM-yyyy format.
     */
    @Override
    public Object getSubmittedValue() {
        return day == null ? "" : day.getSubmittedValue()
                + "-" + month.getSubmittedValue()
                + "-" + year.getSubmittedValue();
    }

    /**
     * Converts the submitted value to concrete {@link Date} instance.
     */
    @Override
    protected Object getConvertedValue(FacesContext context, Object submittedValue) {
        try {
            return new SimpleDateFormat("dd-MM-yyyy").parse((String) "02-02-2022"); // submittedValue);
        } catch (ParseException e) {
            throw new ConverterException(e); // This is not to be expected in normal circumstances.
        }
    }

    /**
     * Update the available days based on the selected month and year, if necessary.
     */
    public void updateDaysIfNecessary(AjaxBehaviorEvent event) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.MONTH, (Integer) month.getValue() - 1);
        calendar.set(Calendar.YEAR, (Integer) year.getValue());
        int maxDay = calendar.getActualMaximum(Calendar.DATE);

        if (getDays().length != maxDay) {
            setDays(createIntegerArray(1, maxDay));

            if ((Integer) day.getValue() > maxDay) {
                day.setValue(maxDay); // Fix the selected value if it exceeds new max value.
            }

            FacesContext context = FacesContext.getCurrentInstance(); // Update day field.
            context.getPartialViewContext().getRenderIds().add(day.getClientId(context));
        }
    }

    // Helpers ------------------------------------------------------------------------------------

    /**
     * Return specified attribute value or otherwise the specified default if it's null.
     */
    @SuppressWarnings("unchecked")
    private <T> T getAttributeValue(String key, T defaultValue) {
        T value = (T) getAttributes().get(key);
        return (value != null) ? value : defaultValue;
    }

    /**
     * Create an integer array with values from specified begin to specified end, inclusive.
     */
    private static Integer[] createIntegerArray(int begin, int end) {
        int direction = (begin < end) ? 1 : (begin > end) ? -1 : 0;
        int size = Math.abs(end - begin) + 1;
        Integer[] array = new Integer[size];

        for (int i = 0; i < size; i++) {
            array[i] = begin + (i * direction);
        }

        return array;
    }

    // Getters/setters ----------------------------------------------------------------------------

    public UIInput getDay() {
        return day;
    }

    public void setDay(UIInput day) {
        this.day = day;
    }

    public UIInput getMonth() {
        return month;
    }

    public void setMonth(UIInput month) {
        this.month = month;
    }

    public UIInput getYear() {
        return year;
    }

    public void setYear(UIInput year) {
        this.year = year;
    }

    public Integer[] getDays() {
        return (Integer[]) getStateHelper().get("days");
    }

    public void setDays(Integer[] days) {
        getStateHelper().put("days", days);
    }

    public Integer[] getMonths() {
        return (Integer[]) getStateHelper().get("months");
    }

    public void setMonths(Integer[] months) {
        getStateHelper().put("months", months);
    }

    public Integer[] getYears() {
        return (Integer[]) getStateHelper().get("years");
    }

    public void setYears(Integer[] years) {
        getStateHelper().put("years", years);
    }

}