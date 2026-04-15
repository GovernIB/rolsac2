function inicializarDatePickers() {
    $.each(PrimeFaces.widgets, function(widgetVar, widget) {
        if (!widget || typeof widget.getDate !== 'function' || typeof widget.setDate !== 'function') {
            return;
        }

        var trigger = widget.jq ? widget.jq.find('.ui-datepicker-trigger') : $();
        if ((!trigger || trigger.length === 0) && widget.input) {
            trigger = widget.input.parent().find('.ui-datepicker-trigger');
        }

        if (!trigger || trigger.length === 0) {
            return;
        }

        trigger.off('mousedown.defaultDate').on('mousedown.defaultDate', function() {
            if (!widget.getDate()) {
                var today = new Date();
                today.setHours(0, 1, 0, 0);
                widget.setDate(today);
            }
        });
    });
}

$(document).ready(function() {
    inicializarDatePickers();
});