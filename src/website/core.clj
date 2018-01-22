(ns website.core
  (:require [hiccup.core :as hiccup]
            [hiccup.page :as page]
            [garden.core :as g]
            [garden.units :as gu]
            [garden.selectors :as gs]
            [garden.stylesheet :as stylesheet]))

(defn main-template
  "Map-> Hiccup
  Main page template"
  [ctxt]
  [:html {:lang "en-us"}
   [:head
    [:meta {:charset "utf-8"}]
    [:title (str "Montessori Bloom" (:title ctxt ""))]
    [:meta {:name "description"
            :content (:description ctxt "")}]
    (for [css-href (:css-hrefs ctxt [])]
      [:link {:rel "stylesheet"
              :href css-href}])]
   [:body [:div#page (:content ctxt [:p "content"])]]])

(defn to-string
  "Hiccup -> String"
  [content]
  (hiccup/html
    {:mode :xml}
    (page/doctype :html5)
    content))

(def navigation
  [:nav
   [:ul
    [:li [:a {:href "index.html"} "Home"]]
    [:li [:a {:href "about.html"} "About Ms. Atsuko"]]
    [:li [:a {:href "newsletters.html"} "Newsletters"]]
    [:li [:a {:href "resources.html"} "Useful Resources"]]]])

(def footer
  [:footer
   [:p "&copy; Atsuko O'Keefe, 2018. All rights reserved"]])

(def css-files ["css/normalize.css"
                "css/main.css"
                "css/custom.css"])

(def custom-css
  (g/css [:p {:font-family "'Libre Baskerville', serif"}] 
         [:#page {:max-width (gu/px 940)
                  :min-width (gu/px 720)
                  :margin "10px auto 10px auto"
                  :padding (gu/px 20)
                  :background-color "#ffffff"}]
         [:h1 {:margin-right "auto"
               :margin-left "auto"
               :text-align "center"}]
         [:body {:background-color "rgb(255, 228, 228)"}]
         [:footer {:border-top "1px solid #000"}]
         [(gs/> :footer :p) {:font-size "smaller"}]
         [(gs/> :nav :ul) {:width (gu/px 570) 
                           :padding (gu/px 15)
                           :margin "0px auto 0px auto"
                           :border-top "2px solid #000"
                           :border-bottom "1px solid #000"
                           :text-align "center"}]
         [(gs/> :nav :ul :li) {:display "inline"
                               :margin "0px 3px"
                               :border-right "1px solid #000"
                               :padding-right (gu/em 1)}]
         [(gs/> :nav :ul :li:last-child) {:border-right "none"
                                          :padding-right (gu/px 0)}]))

(def main-ctxt
  {:title ""
   :description "Website of Ms. Atsuko at Lincoln Elementary"
   :content (list navigation
              [:h1 "Montessori Bloom"]
              [:h2 "Ms. Atsuko's Lower Elementary Class Home Page"]
              footer)
   :css-hrefs css-files})

(def about-ctxt
  {:title ": About Ms. Atsuko"
   :description "About Atsuko O'Keefe, Montessori Teacher for Lower Elementary at Lincoln Elementary School."
   :content (list navigation
              [:h1 "About Me"]
              [:p "I was born and raised in "
               [:a {:href "https://kyoto.travel/en"} "Kyoto, Japan"]
               ". I was quite a tomboy growing up.  My neighbor friend,
               Satoshi and I used to bike around our neighborhood all the time; one of
               us would come down a hill, the other would go up the hill, and we would
               crash into each other! I have a picture at home where I am smiling big
               and Satoshi is smiling, also, but with bandages wrapped around his
               head."]
              [:p "I worked as a preschool teacher in Japan. That was where I learned
               about Montessori education. I took a Montessori Primary School workshop
               in Japan, and I couldn’t believe what I was hearing about how children
               would learn in a Montessori classroom. I had to go and observe a
               classroom for myself. That is when I fell in love with Montessori
               education."]
              [:p "I took a primary teacher training in British Columbia, Canada. My
               trainer, Mrs. Nicole Marchuck looked like Dr. Maria Montessori herself
               &mdash; very peaceful, patient, and loving. It was my very first time to
               learn things in English. You can imagine how patient and loving she
               needed to be!  After the training, I got a job in San Mateo, CA. There
               were three teachers in the classroom: one speaking in English, one
               speaking in Chinese, and one speaking in Japanese (me). The children in the
               classroom naturally learned those three languages. I loved working with
               my students, but an elementary Montessori teacher, who was my coworker,
               inspired me about elementary Montessori education. That was when I decided to
               move to Milwaukee, WI to take the elementary teacher training."]
              [:p "This eventually lead me to come to Colorado! After the training I
               worked at a private school in Denver for a couple of years. Those
               children were absolutely wonderful, but I wanted to work at a public
               school where I can help make Montessori education available to many more
               children. It was again time for me to go back to school to get my
               Colorado State teacher’s license. During that time, I came to Lincoln
               Elementary School and observed Ms. Therese’s room. My student teaching
               practice was held in Kinder/1st grade traditional class at Lincoln as
               well!  At the end of my student teaching, I learned that Lincoln was
               opening its second Montessori Elementary class. I asked the principal
               (Mrs. Daphne Hunter at that time) if I could apply for the new position.
               She said, “Oh, unfortunately, it is a <em>Montessori</em> teacher’s
               position.” I said, “Yes, I am qualified for that.” She said again, “Oh!
               That’s right! Please apply!” … So here I am; this is my 12<sup>th</sup>
               year at Lincoln. Since starting at Lincoln, I taught 4 years in lower
               elementary class and then moved up to teach 5 years in upper elementary
               and then came back to lower elementary. This is my third year teaching
               the lower elementary once again."]
              [:p "I am one blessed teacher to be able to live in this country and work
               with such wonderful children and families!"]
              footer)
   :css-hrefs css-files})

(def resources-ctxt
  {:title ": Useful Resources"
   :description "Website of Ms. Atsuko at Linconl Elementary"
   :content (list navigation
              [:h1 "Useful References"]
              footer)
   :css-hrefs css-files})

(def newsletter-ctxt
  {:title ": Newsletter Archive"
   :description "Newsletters archive for Ms. Atsuko's Montessori Class"
   :content (list navigation
              [:h1 "Newsletters"]
              footer)
   :css-hrefs css-files})

(def pages
  {"about.html" (to-string (main-template about-ctxt))
   "index.html" (to-string (main-template main-ctxt))
   "newsletters.html" (to-string (main-template newsletter-ctxt))
   "resources.html" (to-string (main-template resources-ctxt))})

(def output-path "resources/generated")

(defn -main []
  (let [css-custom-path "resources/generated/css/custom.css"]
    (spit css-custom-path custom-css))
  (loop [ps pages]
    (if ps
      (let [[page-path txt] (first ps)]
        (spit (str output-path "/" page-path) txt)
        (recur (next ps)))))
  (println "Done!"))
