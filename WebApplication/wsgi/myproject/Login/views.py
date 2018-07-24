from django.shortcuts import render, redirect
from django.template import loader
from django.template.loader import render_to_string
from django.utils.decorators import method_decorator
from .models import Users, Issues, Intiatedandcompletejobs, Builder, Property, Tennant
from django.http import HttpResponse
from django.http import JsonResponse
import json
from django.core import serializers
from django.db import IntegrityError
import datetime


# Create your views here.
def index(request):
    # load all articles from the database and loads the caterories that exist in the DB, also checks if user is logged in and sends all this trhough via context variable, it loads the index template
    if request.session.has_key('username'):  # check if user is logged in and sends a unique context variable
        template = loader.get_template('login.html')
        return render(request, template)
    else:
        return loginPage(request)


def loginPage(request):
    template = loader.get_template('login.html')
    return render(request, 'login.html', {'userloggedIn': False})


# check user name and password matches the username and password on the database and sets the sessionID
def login(request):
    Ausers = request.POST.get("formData[1][value]", '')
    Apassword = request.POST.get("formData[2][value]", '')
    if Users.objects.filter(username=Ausers, password=Apassword):
        request.session['username'] = Ausers
        request.session['password'] = Apassword
        key = list(Users.objects.filter(username=Ausers, password=Apassword).values_list())
        return JsonResponse(key, safe=False)
    else:
        return null
        
def getIndex(request, type, pk):
    # load all articles from the database and loads the caterories that exist in the DB, also checks if user is logged in and sends all this trhough via context variable, it loads the index template
    if request.session.has_key('username'):  # check if user is logged in and sends a unique context variable
        if (type == "builder"):
            articles = Issues.objects.filter(intiatedandcompletejobs__builderassigned=pk).exclude(status="Complete").order_by("date").reverse()
            context = {'userloggedIn': True, 'userType': type, 'articleTitle': articles, 'builder': pk}
            return render(request, 'index.html', context)
        else:
            user = Tennant.objects.get(tennantid=pk)
            userProperty = Property.objects.get(propertyid=user.livesat_id)
            articles = Issues.objects.filter(propertyid=user.livesat).exclude(status="Complete").order_by("date").reverse()
            context = {'userloggedIn': True, 'userType': type, 'articleTitle': articles, 'user': user}
            return render(request, 'index.html', context)
    else:
        return render(request, 'login.html', {'userloggedIn': False})


def getJobDetail(request, builderpk, pk):
    articles = Issues.objects.filter(intiatedandcompletejobs__builderassigned=builderpk,
                                     intiatedandcompletejobs__issueid=pk).values()
    inited = Intiatedandcompletejobs.objects.filter(issueid=pk).values()
    property = Property.objects.get(pk=articles[0]['propertyid_id'])
    type='builder'

    context = {'userloggedIn': True, 'userType': type, 'userPK':builderpk,'articleTitle': articles, 'property': property,
               'initated': inited}
    return render(request, 'jobDetail.html', context)


def jobUpdate(request, pk):
    if request.session.has_key('username'):  # check if user is logged in and sends a unique context variable
        Ausers = request.session['username']
        Apassword = request.session['password']
        now = datetime.datetime.now()
        articles = Issues.objects.filter(intiatedandcompletejobs__builderassigned=request.POST.get('builderPk'),
                                         intiatedandcompletejobs__issueid=pk).values()
        inited = Intiatedandcompletejobs.objects.filter(issueid=pk)
        type = request.POST.get('buttonValue')
        job = Intiatedandcompletejobs.objects.get(issueid=pk)
        issue = Issues.objects.get(idissues=pk)
        if (type == "Job Complete"):
            job.comments = job.comments + " " + now.strftime('%Y-%m-%d') + ":" + request.POST.get('comment');
            job.jobcompleted = now.strftime('%Y-%m-%d')
            issue.status = 'Complete';
            job.save();
            issue.save();
        else:
            job.comments = job.comments + request.POST.get('comment');
            job.save();

        articles = Issues.objects.filter(intiatedandcompletejobs__builderassigned=pk)
        key = list(Users.objects.filter(username=Ausers, password=Apassword).values_list())
        return JsonResponse(key, safe=False)
    else:
        return HttpResponse("need_to_login")

def getissue(request,pk,issueID):
    articles = Issues.objects.get(pk=issueID)
    user = Tennant.objects.get(tennantid=pk)

    initeda = Intiatedandcompletejobs.objects.filter(issueid=articles)
    if initeda.exists():
        initeda = Intiatedandcompletejobs.objects.filter(issueid=articles).values()
        inited = Intiatedandcompletejobs.objects.get(issueid=articles)
        userProperty = Property.objects.get(propertyid=user.livesat_id)
        builder=Builder.objects.get(pk=initeda[0]['builderassigned_id'])
        context = {'userloggedIn': True, 'userType': type, 'articleTitle': articles, 'property': userProperty,
                   'initated': inited,'user':user,'builder':builder}
        return render(request, 'issueDetail.html', context)
    else:
        userProperty = Property.objects.get(propertyid=user.livesat_id)
        context = {'userloggedIn': True, 'userType': type, 'articleTitle': articles, 'property': userProperty,
                   'initated': False,'user':user}
        return render(request, 'issueDetail.html', context)
def getReportPage(request, pk):
    print("dfs")
    if request.session.has_key('username'):  # check if user is logged in and sends a unique context variable
        user=Tennant.objects.get(tennantid=pk)
        userProperty=Property.objects.get(propertyid=user.livesat_id)
        context={'userloggedIn': True, 'userType': type,'user':user,'userProperty':userProperty}
        return render(request,'issueReporting.html',context)
    else:
        return null

def reportIssue(request, pk):
    Ausers = request.session['username']
    Apassword = request.session['password']
    now = datetime.datetime.now()
    user = Tennant.objects.get(tennantid=pk)
    userProperty = Property.objects.get(propertyid=user.livesat_id)
    issue=request.POST.get("comment")
    newissue=Issues(description=issue,reportedby=user.firstname+" "+user.lastname,date=now.strftime('%Y-%m-%d'),status="Open",levelofurgency="low",propertyid=userProperty)
    newissue.save()
    user = Tennant.objects.filter(tennantid=pk)
    articles = Issues.objects.filter(idissues=pk)
    context = {'userloggedIn': True, 'userType': type, 'articleTitle': articles, 'user': user}
    template = loader.get_template('index.html')
    print(request)
    print("d")
    return HttpResponse("True")

def logout(request):  # deletes the session ID and loads index template
    print("logging out and flushing session")
    request.session.flush()
    return index(request)
