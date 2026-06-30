function inicializarDatePickers() {
    $.each(PrimeFaces.widgets, function(widgetVar, widget) {
        if (!widget || typeof widget.getDate !== 'function' || typeof widget.setDate !== 'function') {
            return;
        }

        bloquearDatePickerReadonly(widget);

        var aplicarFechaPorDefecto = function() {
            if (!widget.getDate()) {
                var today = new Date();
                today.setHours(0, 1, 0, 0);
                widget.setDate(today);
            }
        };

        var trigger = widget.jq ? widget.jq.find('.ui-datepicker-trigger') : $();
        if ((!trigger || trigger.length === 0) && widget.input) {
            trigger = widget.input.parent().find('.ui-datepicker-trigger');
        }

    if (!widget.input) {
            if (!trigger || trigger.length === 0) {
                return;
            }

            trigger.off('mousedown.defaultDate').on('mousedown.defaultDate', function() {
                aplicarFechaPorDefecto();
            });

            return;
        }

        widget.input.off('mousedown.defaultDate click.defaultDate focus.defaultDate')
            .on('mousedown.defaultDate click.defaultDate focus.defaultDate', function() {
                aplicarFechaPorDefecto();
            });

        if (!trigger || trigger.length === 0) {
            return;
        }

        trigger.off('mousedown.defaultDate').on('mousedown.defaultDate', function() {
            aplicarFechaPorDefecto();
        });
    });
}

function bloquearDatePickerReadonly(widget) {
    var isReadOnly = false;

    if (widget.cfg && (widget.cfg.readonly === true || widget.cfg.readOnly === true || widget.cfg.readonlyInput === true)) {
        isReadOnly = true;
    }

    if (!isReadOnly && widget.input && widget.input.prop && widget.input.prop('readonly')) {
        isReadOnly = true;
    }

    if (!isReadOnly) {
        return;
    }

    if (typeof widget.show === 'function') {
        widget.show = function () {};
    }
    if (typeof widget.showOverlay === 'function') {
        widget.showOverlay = function () {};
    }

    var dom = widget.input ? widget.input.get(0) : null;

    if (dom && dom._bloqueoReadonlyNodoActual !== true) {
        dom._bloqueoReadonlyNodoActual = true;

        ['mousedown', 'click', 'focus'].forEach(function (evtName) {
            dom.addEventListener(evtName, function (e) {
                e.stopPropagation();
                if (e.stopImmediatePropagation) {
                    e.stopImmediatePropagation();
                }
            }, true);
        });

        var teclasQueAbren = ['ArrowDown', 'Down', 'F4', ' ', 'Spacebar', 'Enter'];
        dom.addEventListener('keydown', function (e) {
            if (e.ctrlKey || e.metaKey) {
                return;
            }
            if (teclasQueAbren.indexOf(e.key) !== -1) {
                e.preventDefault();
                e.stopPropagation();
                if (e.stopImmediatePropagation) {
                    e.stopImmediatePropagation();
                }
            }
        }, true);
    }

    if (widget.jq) {
        var $triggers = widget.jq.find('.ui-datepicker-trigger, .ui-datepicker-button, .ui-datepicker-toggler, [role=button]');
        $triggers.each(function () {
            if (!this._bloqueoReadonlyAplicado) {
                this._bloqueoReadonlyAplicado = true;
                this.addEventListener('click', function (e) {
                    e.preventDefault();
                    e.stopPropagation();
                    if (e.stopImmediatePropagation) {
                        e.stopImmediatePropagation();
                    }
                }, true);
            }
        });
    }
}

$(document).ready(function() {
    inicializarDatePickers();
});