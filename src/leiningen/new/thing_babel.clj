(ns leiningen.new.thing-babel
  (:require
   [leiningen.new.templates :as tpl]
   [leiningen.core.main :as main]
   [clojure.string :as str])
  (:import
   [java.util Locale Calendar]))

(def licenses
  {"asl" {:name "Apache Software License 2.0" :url "http://www.apache.org/licenses/LICENSE-2.0"}
   "epl" {:name "Eclipse Public License" :url "http://www.eclipse.org/legal/epl-v10.html"}
   "mit" {:name "MIT License" :url "http://opensource.org/licenses/MIT"}})

(def render (tpl/renderer "thing-babel"))

(defn group-name
  "Replace hyphens with underscores."
  [^String s]
  (let [idx (.indexOf s "/")]
    (when (pos? idx)
      (subs s 0 idx))))

(defn opts-info
  [opts ks]
  (doseq [[k desc] (partition 2 ks)]
    (main/info (format "%-24s: %s" desc (str (opts k))))))

(defn thing-babel
  "Literal programming template for org-mode babel projects"
  [name & args]
  (let [opts (->> args
                  (partition 2)
                  (map #(vector (keyword (first %)) (second %)))
                  (into {}))
        {:keys [author license target url]
         :or   {author (System/getProperty "user.name")
                license "epl"
                target "babel"
                url "https://github.com/"}} opts
        tangle-target (if (and (seq target) (not= \/ (last target)))
                        (str target \/)
                        target)
        target        (if (empty? target) "project root" target)
        group         (group-name name)
        license       (.toLowerCase license)
        opts          (merge
                       {:name (tpl/project-name name)
                        :group group
                        :fqname name
                        :sanitized (tpl/name-to-path name)
                        :author author
                        :url url
                        :desc "FIXME: write description"
                        :license-name (get-in licenses [license :name])
                        :license-url (get-in licenses [license :url])
                        :ns-root (tpl/sanitize-ns name)
                        :ns-root-path (tpl/name-to-path name)
                        :tangle-target tangle-target
                        :target target
                        :tzone (-> (Locale/getDefault)
                                   (Calendar/getInstance)
                                   (.get Calendar/ZONE_OFFSET)
                                   (/ (* 1000 60 60)))}
                       opts)]
    (main/info (str "Generating fresh literate programming project: " name))
    (opts-info opts
               [:name          "generated project dir"
                :group         "artefact group ID"
                :url           "project url"
                :author        "project author"
                :author-url    "author url"
                :email         "author email"
                :tzone         "author timezone"
                :license-name  "license"
                :desc          "description"
                :tangle-target "path for gen sources"
                :ns-root       "project root namespace"])
    (tpl/->files opts
                 ["README.org"             (render "readme.org" opts)]
                 ["src/setup.org"          (render "setup.org" opts)]
                 ["src/core.org"           (render "core.org" opts)]
                 ["src/libraryofbabel.org" (render "libraryofbabel.org" opts)]
                 ["test/core.org"          (render "test.org" opts)]
                 ["tangle.sh"              (render "tangle.sh") :executable true]
                 ["tangle-all.sh"          (render "tangle-all.sh") :executable true])))
