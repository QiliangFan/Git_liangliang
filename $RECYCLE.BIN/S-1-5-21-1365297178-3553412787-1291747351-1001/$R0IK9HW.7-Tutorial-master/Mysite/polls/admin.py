from django.contrib import admin
from .models import Question, Choice


# Register your models here.
# admin.site.register(Question)
# class QuestionAdmin(admin.ModelAdmin):
#     fields = ['pub_date', 'question_text']

# class ChoiceInline(admin.StackedInline):
#     model = Choice
#     extra = 1

class ChoiceInline(admin.TabularInline):
    model = Choice
    extra = 0


class QuestionAdmin(admin.ModelAdmin):
    fieldsets = [
        (None, {'fields': ['question_text']}),
        ('Date informatios', {'fields': ['pub_date']}),
    ]
    inlines = [ChoiceInline]
    list_display = ('question_text', 'pub_date')
    search_fields = ['question_text']


admin.site.register(Question, QuestionAdmin)
# admin.site.register(Choice)
