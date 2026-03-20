Imports System.IO
Imports System.Net
Imports System.Web.Mvc
Imports Newtonsoft.Json
Imports Newtonsoft.Json.Linq

Namespace Controllers
    Public Class smsController
        Inherits Controller


        <HttpPost()>
        Function Credits(ByVal apikey As String) As JObject

            If String.IsNullOrEmpty(apikey) = False Then
                apikey = apikey.Trim()

                Dim Request As WebRequest = WebRequest.Create("https://api.smsmasivos.com.mx/credits/consult")
                Dim Response As HttpWebResponse
                Dim ResponseContent As String

                With Request
                    .Method = "POST"
                    .ContentType = "application/x-www-form-urlencoded"
                    .Proxy = Nothing
                    .Headers.Add("apikey", apikey)


                    ' .UserAgent = "Mozilla/5.0 (Windows NT 6.2; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0"
                End With

                ' Get the response.
                Response = Request.GetResponse()

                ' Get the response content.
                Using Reader As New System.IO.StreamReader(Request.GetResponse().GetResponseStream)
                    ResponseContent = Reader.ReadToEnd
                    Response.Close()
                End Using

                ' Return the response content.
                Dim jResults As JObject = JObject.Parse(ResponseContent)
                Return jResults


            End If

        End Function


        <HttpPost()>
        Function Send(ByVal apikey As String, ByVal country As String, ByVal dest As String, ByVal texto As String, ByVal sand As String, ByVal nombre As String) As JObject

            Dim Data As String = $"message={texto}&"
            Data += $"numbers={dest}&"
            Data += $"country_code={country}&"
            Data += $"sandbox={sand}&"
            Data += $"name={nombre}"

            Dim Request As WebRequest = WebRequest.Create("https://api.smsmasivos.com.mx/sms/send")
            Dim Response As HttpWebResponse
            Dim ResponseContent As String

            With Request
                .Method = "POST"
                .ContentType = "application/x-www-form-urlencoded"
                .ContentLength = Data.Length
                .Proxy = Nothing
                .Headers.Add("apikey", apikey)


                ' .UserAgent = "Mozilla/5.0 (Windows NT 6.2; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0"
            End With

            ' Write the POST data bytes into the Stream.
            Using RequestStream As IO.Stream = Request.GetRequestStream()
                RequestStream.Write(System.Text.Encoding.UTF8.GetBytes(Data.ToString), 0, Data.ToString.Length)
            End Using

            ' Get the response.
            Response = Request.GetResponse()

            ' Get the response content.
            Using Reader As New System.IO.StreamReader(Request.GetResponse().GetResponseStream)
                ResponseContent = Reader.ReadToEnd
                Response.Close()
            End Using

            ' Return the response content.
            Dim jResults As JObject = JObject.Parse(ResponseContent)
            Return jResults
        End Function

        <HttpPost()>
        Function Register(ByVal apikey As String, ByVal nombre As String, ByVal country As String, ByVal dest As String, ByVal digitos As String, ByVal formato As String) As JObject


            Dim Data As String = $"phone_number={dest}&"
            Data += $"country_code={country}&"
            Data += $"company={nombre}&"
            Data += $"code_length={digitos}"

            Dim URL As String = "https://api.smsmasivos.com.mx/protected/" & formato & "/phones/verification/start"

            Dim Request As WebRequest = WebRequest.Create(URL)
            Dim Response As HttpWebResponse
            Dim ResponseContent As String

            With Request
                .Method = "POST"
                .ContentType = "application/x-www-form-urlencoded"
                .ContentLength = Data.Length
                .Proxy = Nothing
                .Headers.Add("apikey", apikey)


                ' .UserAgent = "Mozilla/5.0 (Windows NT 6.2; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0"
            End With

            ' Write the POST data bytes into the Stream.
            Using RequestStream As IO.Stream = Request.GetRequestStream()
                RequestStream.Write(System.Text.Encoding.UTF8.GetBytes(Data.ToString), 0, Data.ToString.Length)
            End Using

            ' Get the response.
            Response = Request.GetResponse()

            ' Get the response content.
            Using Reader As New System.IO.StreamReader(Request.GetResponse().GetResponseStream)
                ResponseContent = Reader.ReadToEnd
                Response.Close()
            End Using

            If (formato = "xml") Then
                Dim doc = XDocument.Parse(ResponseContent)
                Dim val As String = doc.Root.Element("message")
                Dim text As String = "{""message"":""" & val & """}"
                ResponseContent = text

            End If

            ' Return the response content.
            Dim jResults As JObject = JObject.Parse(ResponseContent)
            Return jResults

        End Function

        <HttpPost()>
        Function Validate(ByVal apikey As String, ByVal code As String, ByVal dest As String, ByVal formato As String) As JObject

            Dim Data As String = $"phone_number={dest}&"
            Data += $"verification_code={code}"

            Dim URL As String = "https://api.smsmasivos.com.mx/protected/" & formato & "/phones/verification/check"

            Dim Request As WebRequest = WebRequest.Create(URL)
            Dim Response As HttpWebResponse
            Dim ResponseContent As String

            With Request
                .Method = "POST"
                .ContentType = "application/x-www-form-urlencoded"
                .ContentLength = Data.Length
                .Proxy = Nothing
                .Headers.Add("apikey", apikey)


                ' .UserAgent = "Mozilla/5.0 (Windows NT 6.2; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0"
            End With

            ' Write the POST data bytes into the Stream.
            Using RequestStream As IO.Stream = Request.GetRequestStream()
                RequestStream.Write(System.Text.Encoding.UTF8.GetBytes(Data.ToString), 0, Data.ToString.Length)
            End Using

            ' Get the response.
            Response = Request.GetResponse()

            ' Get the response content.
            Using Reader As New System.IO.StreamReader(Request.GetResponse().GetResponseStream)
                ResponseContent = Reader.ReadToEnd
                Response.Close()
            End Using

            If (formato = "xml") Then
                Dim doc = XDocument.Parse(ResponseContent)
                Dim val As String = doc.Root.Element("message")
                Dim text As String = "{""message"":""" & val & """}"
                ResponseContent = text

            End If

            ' Return the response content.
            Dim jResults As JObject = JObject.Parse(ResponseContent)
            Return jResults
        End Function

    End Class

End Namespace
