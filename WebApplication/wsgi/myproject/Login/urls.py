from django.conf.urls import url

from.import views

urlpatterns= [

url(r'^$' , views.loginPage),
url(r'login',views.login),
url(r'logOut',views.logout),
url(r'Login',views.getIndex),
url(r'^(?P<type>[A-Z-a-z]+)/(?P<pk>[0-9-]+)/home?',views.getIndex),
url(r'^(?P<builderpk>[0-9-]+)/(?P<pk>[0-9-]+)/jobDetail?',views.getJobDetail),
url(r'(?P<pk>[0-9-]+)/jobUpdate?',views.jobUpdate),
url(r'(?P<pk>[0-9-]+)/reportIssue?',views.getReportPage),
    url(r'(?P<pk>[0-9-]+)/sendReport?', views.reportIssue),
    url(r'(?P<pk>[0-9-]+)/(?P<issueID>[0-9-]+)/issueDetail?', views.getissue),

]